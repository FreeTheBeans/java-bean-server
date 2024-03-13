package com.freethebeans.server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OAuth2Config {

    @Value("${oauth2.authorization-uri}")
    private String oauth2AuthorizationUrl;

    public String getOauth2AuthorizationUrl() {
        return oauth2AuthorizationUrl;
    }

}
