package com.freethebeans.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@Component
public class AccessTokenProvider {

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    public String getAccessToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof OAuth2AuthenticationToken oauthToken) {
            String clientRegistrationId = oauthToken.getAuthorizedClientRegistrationId();

            if (clientRegistrationId != null) {
                OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(clientRegistrationId, oauthToken.getName());

                if (authorizedClient != null) {
                    OAuth2AccessToken accessToken = authorizedClient.getAccessToken();

                    if (accessToken != null) {
                        return accessToken.getTokenValue();
                    }
                }
            }
        }

        return null;
    }
    public void revokeAccessToken() {
        try {
            String accessToken = getAccessToken();

            if (accessToken != null) {
                String revokeUrl = "https://oauth2.googleapis.com/revoke?token=" + accessToken;

                Map<String, String> request = new HashMap<>();
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.postForObject(revokeUrl, request, String.class);
            }
        } catch (Exception e) {
            System.err.println("Error revoking access token: " + e.getMessage());
        }
    }


}
