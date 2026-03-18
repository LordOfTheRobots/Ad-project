package com.ad.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CollectorConfigJson {
    private String defaultType = "youtube";
    private Map<String, CollectorDefinition> collectors = new HashMap<>();

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CollectorDefinition {
        private ComponentConfig client;
        private ComponentConfig collector;
        private ComponentConfig queryMaker;
        private ParamsConfig params;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ComponentConfig {
        private String clazz;

        @JsonProperty("class")
        public String getClassName() { return clazz; }

        private String baseUrl;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ParamsConfig extends ComponentConfig {
        private String builderMethod = "builder";
        private Map<String, Object> properties = new HashMap<>();
    }
}
