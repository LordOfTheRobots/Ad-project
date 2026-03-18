package com.ad.config;

import com.ad.apiparams.Params;
import com.ad.collectorapi.Collector;
import com.ad.query.QueryMaker;
import com.ad.webclient.Client;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
public class UnifiedCollectorConfig {

    private final JsonComponentLoader loader;

    public UnifiedCollectorConfig(JsonComponentLoader loader) {
        this.loader = loader;
    }

    @Bean
    @RequestScope(proxyMode = ScopedProxyMode.INTERFACES)
    public Client client() {
        String baseUrl = loader.getBaseUrl();
        return loader.createHttpClient(Client.class, baseUrl);
    }

    @Bean
    public Collector collector() {
        return loader.createComponent("collector", Collector.class);
    }

    @Bean
    public QueryMaker queryMaker() {
        return loader.createComponent("queryMaker", QueryMaker.class);
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Params params() {
        return loader.createParams();
    }
}
