package com.freethebeans.client;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class JavaBeanClientApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(JavaBeanClientApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String serverUrl = "http://localhost:8081/api/hello";
        String response = new RestTemplate().getForObject(serverUrl, String.class);

        System.out.println("Response from Server: " + response);
    }
}

