package com.freethebeans.server.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

@Controller
public class OAuth2LoginController {

    private final ClientRegistrationRepository clientRegistrationRepository;

    @Value("${server.servlet.context-path}")
    private String servletContextPath;


    public OAuth2LoginController(ClientRegistrationRepository clientRegistrationRepository) {
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

    @GetMapping("/oauth2/login")
    public String getOAuthLogin(Model model) {
        ClientRegistration googleRegistration = clientRegistrationRepository.findByRegistrationId("google");
        if (googleRegistration != null) {
            String authUrl = googleRegistration.getProviderDetails().getAuthorizationUri() +
                    "?client_id=" + googleRegistration.getClientId() +
                    "&redirect_uri=" + getRedirectUri() +
                    "&response_type=code" +
                    "&scope=" + String.join("+", googleRegistration.getScopes());
            model.addAttribute("authUrl", authUrl);
            return "oauth2_login";
        } else {
            return "error";
        }
    }

    private String getRedirectUri() {
        return "http://localhost:" + getPort() + servletContextPath + "/oauth2/callback";
    }

    private int getPort() {
        return Integer.parseInt(System.getProperty("server.port"));
    }
}