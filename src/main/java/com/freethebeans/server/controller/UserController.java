package com.freethebeans.server.controller;

import com.freethebeans.server.config.AccessTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class UserController {

    @Autowired
    private AccessTokenProvider accessTokenProvider;

    @GetMapping()
    public String welcome(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            String givenName = getAttribute(authentication, "given_name");
            return "Hello " + givenName + "!, you are logged in with Google. Please close the tab.";
        } else {
            return "No current user. Please log in.";
        }
    }

    private String getAttribute(Authentication authentication, String attributeName) {
        Object principal = authentication.getPrincipal();

        if (principal instanceof OAuth2User) {
            return ((OAuth2User) principal).getAttribute(attributeName);
        } else {
            return authentication.getName();
        }

    }

    @GetMapping("/user-logout")
    public String revokeAccessToken(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        accessTokenProvider.revokeAccessToken();
        return "Successfully logged out";
    }

}


