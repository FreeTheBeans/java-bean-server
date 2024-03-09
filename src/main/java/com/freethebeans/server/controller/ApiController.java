package com.freethebeans.server.controller;

import com.freethebeans.server.model.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {
    DBConnection dbConnection = new DBConnection();

    @GetMapping("/hello")
    public ApiResponse hello() {
        return new ApiResponse("Hello, client! This is a stateless Spring REST API.");
    }

    @GetMapping("/ping")
    public ApiResponse ping() {
        return new ApiResponse("pong");
    }

    @GetMapping("/dummy")
    public ApiResponse dummy() {
        dbConnection.getDummyRecord();
        return new ApiResponse("dummy");
    }
}