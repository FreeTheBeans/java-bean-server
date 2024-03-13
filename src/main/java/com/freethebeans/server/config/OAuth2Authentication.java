package com.freethebeans.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

@Component
public class OAuth2Authentication {

    private final OAuth2Config oAuth2Config;
    private final Scanner scanner;
    public String logoutLink = "http://localhost:31415/user-logout";

    @Autowired
    public OAuth2Authentication(OAuth2Config oAuth2Config) {
        this.oAuth2Config = oAuth2Config;
        this.scanner = new Scanner(System.in);
    }

    public void performAuthentication() {
        while (true) {
            System.out.print("Login? (yes/no): ");
            String userInput = scanner.nextLine().toLowerCase();

            if ("yes".equals(userInput)) {
                CountDownLatch latch = new CountDownLatch(1);
                performOAuth2Authentication(latch);
                latch.countDown();
            } else if ("no".equals(userInput)) {
                System.out.println("logout with");
                System.out.println(logoutLink);
            } else {
                System.out.println("Invalid input. Please enter 'yes' or 'no'.");
            }
        }
    }

    private void performOAuth2Authentication(CountDownLatch latch) {
        System.out.println("Login via: " + oAuth2Config.getOauth2AuthorizationUrl());
        latch.countDown();
    }
}



