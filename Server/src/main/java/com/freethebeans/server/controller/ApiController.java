package com.freethebeans.server.controller;

import com.freethebeans.server.model.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping("/hello")
    public ApiResponse hello() {
        return new ApiResponse("Hello, client! This is a stateless Spring REST API.");
    }
}
