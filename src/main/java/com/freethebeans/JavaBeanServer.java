package com.freethebeans;

import com.freethebeans.server.config.OAuth2Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JavaBeanServer implements CommandLineRunner {

    @Autowired
    private OAuth2Authentication oAuth2Authentication;

    public static void main(String[] args) {
        SpringApplication.run(JavaBeanServer.class, args);
    }
    @Override
    public void run(String... args) throws Exception {
        oAuth2Authentication.performAuthentication();
    }
}