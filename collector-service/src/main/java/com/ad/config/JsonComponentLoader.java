package com.ad.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class JsonComponentLoader {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Environment environment;

    private CollectorConfigJson config;
    private final Map<String, Class<?>> classCache = new ConcurrentHashMap<>();

    public JsonComponentLoader(Environment environment) {
        this.environment = environment;
    }

    @Value("${collector.config-path:classpath:collector-config.json}")
    private String configPath;

    @PostConstruct
    public void init() throws IOException {
        Resource resource = new PathMatchingResourcePatternResolver()
                .getResource(configPath);
        try (InputStream is = resource.getInputStream()) {
            config = objectMapper.readValue(is, CollectorConfigJson.class);
        }
    }

    public String getActiveType() {
        return environment.getProperty("collector.type", "youtube");
    }

    public <T> T createHttpClient(Class<T> clientInterface, String baseUrl) {
        RestClient restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();

        return HttpServiceProxyFactory
                .builderFor(RestClientAdapter.create(restClient))
                .build()
                .createClient(clientInterface);
    }

    public <T> T createComponent(String componentKey, Class<T> expectedType) {
        String type = getActiveType();
        String className = getClassName(type, componentKey);
        Class<?> clazz = loadClass(className);

        if (!expectedType.isAssignableFrom(clazz)) {
            throw new IllegalStateException("Class %s is not assignable to %s".formatted(className, expectedType.getName()));
        }
        try {
            return expectedType.cast(clazz.getDeclaredConstructor().newInstance());
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate: " + className, e);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T createParams() {
        String type = getActiveType();
        var paramsCfg = Optional
                .ofNullable(config.getCollectors().get(type))
                .map(CollectorConfigJson.CollectorDefinition::getParams)
                .orElseThrow(() -> new IllegalStateException("No params config for type: " + type));

        Class<?> paramsClass = loadClass(paramsCfg.getClassName());

        try {
            Method builderMethod = paramsClass.getMethod(paramsCfg.getBuilderMethod());
            Object builder = builderMethod.invoke(null);

            for (var prop : paramsCfg.getProperties().entrySet()) {
                Method setter = findSetter(builder.getClass(), prop.getKey());
                if (setter != null) {
                    Object val = objectMapper.convertValue(prop.getValue(), setter.getParameterTypes()[0]);
                    setter.invoke(builder, val);
                }
            }
            return (T) builder.getClass().getMethod("build").invoke(builder);
        } catch (Exception e) {
            throw new RuntimeException("Failed to build params for type: " + type, e);
        }
    }

    public String getBaseUrl() {
        String type = getActiveType();
        return Optional
                .ofNullable(config.getCollectors().get(type))
                .map(CollectorConfigJson.CollectorDefinition::getClient)
                .map(CollectorConfigJson.ComponentConfig::getBaseUrl)
                .map(url -> url.startsWith("http") ? url : "https://" + url)
                .orElseThrow(() -> new IllegalStateException("No baseUrl for type: " + type));
    }

    private Method findSetter(Class<?> clazz, String propertyName) {
        String[] prefixes = {"with", "set"};
        for (String prefix : prefixes) {
            String name = prefix + Character.toUpperCase(propertyName.charAt(0)) + propertyName.substring(1);
            for (Method m : clazz.getMethods()) {
                if (m.getName().equals(name) && m.getParameterCount() == 1) return m;
            }
        }
        return null;
    }

    private Class<?> loadClass(String className) {
        return classCache.computeIfAbsent(className, name -> {
            try {
                return Class.forName(name, true, Thread.currentThread().getContextClassLoader());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Class not found: " + name, e);
            }
        });
    }

    private String getClassName(String type, String componentKey) {
        var def = Optional
                .ofNullable(config.getCollectors().get(type))
                .orElseThrow(() -> new IllegalStateException("Unknown type: " + type));
        return switch (componentKey) {
            case "client" -> Optional.ofNullable(def.getClient()).map(CollectorConfigJson.ComponentConfig::getClassName).orElse(null);
            case "collector" -> Optional.ofNullable(def.getCollector()).map(CollectorConfigJson.ComponentConfig::getClassName).orElse(null);
            case "queryMaker" -> Optional.ofNullable(def.getQueryMaker()).map(CollectorConfigJson.ComponentConfig::getClassName).orElse(null);
            case "params" -> Optional.ofNullable(def.getParams()).map(CollectorConfigJson.ComponentConfig::getClassName).orElse(null);
            default -> null;
        };
    }
}