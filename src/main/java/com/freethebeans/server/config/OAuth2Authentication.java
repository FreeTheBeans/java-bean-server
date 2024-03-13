package com.freethebeans.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

@Component
public class OAuth2Authentication {

    private static final long TIMEOUT_DURATION_MS = 60000;

    @Autowired
    private OAuth2Config oAuth2Config;

    private Scanner scanner;
    public String logoutLink = "http://localhost:31415/user-logout";

    public OAuth2Authentication() {
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
//        if (GraphicsEnvironment.isHeadless()) {
            System.out.println("Login via: " + oAuth2Config.getOauth2AuthorizationUrl());
            latch.countDown();
//        } else {
//            openWebBrowserForLogin(latch);
//        }
    }

//    private void openWebBrowserForLogin(CountDownLatch latch) {
//        try {
//            Desktop desktop = Desktop.getDesktop();
//
//            if (desktop.isSupported(Desktop.Action.BROWSE)) {
//                System.out.println("OAuth2 Authorization URL: " + oAuth2Config.getOauth2AuthorizationUrl());
//
//                URI uri = new URI(oAuth2Config.getOauth2AuthorizationUrl());
//                desktop.browse(uri);
//
//                new Thread(() -> {
//                    try {
//                        Thread.sleep(TIMEOUT_DURATION_MS);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//                    System.out.println("Closing browser...");
//                    System.exit(0);
//                }).start();
//            } else {
//                System.out.println("Unable to open browser. Manually open: " + oAuth2Config.getOauth2AuthorizationUrl());
//                latch.countDown();
//            }
//        } catch (IOException | URISyntaxException e) {
//            System.err.println("Error opening browser: " + e.getMessage());
//            latch.countDown();
//        }
//    }
    }



