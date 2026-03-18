package com.ad.config;

import com.ad.entity.APIKey;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ApiKeysConfig {

    @Bean
    public List<APIKey> apiKeys(Environment environment) {
        List<APIKey> keys = new ArrayList<>();

        int index = 0;
        while (true) {
            String keyValue = environment.getProperty("API_KEY_" + index);
            if (keyValue == null) break;

            String[] parts = keyValue.split("=");
            String apiKey = parts[0].trim();

            int tokens = parts.length > 1
                    ? Integer.parseInt(parts[1].trim())
                    : environment.getProperty("API_KEY_DEFAULT_QUOTA", Integer.class, 10000);

            keys.add(new APIKey(apiKey, tokens));
            index++;
        }

        return keys;
    }
}