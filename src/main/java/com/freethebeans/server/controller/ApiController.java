package com.freethebeans.server.controller;

import com.freethebeans.server.model.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        return new ApiResponse(dbConnection.queryDB("SELECT * FROM states").toString());
    }

    @GetMapping("/state/{stateName}")
    public ApiResponse getStateInformation(@PathVariable String stateName) {
        String stateInformation = dbConnection.fetchStateInformationFromDB(stateName).toString();
        return new ApiResponse(stateInformation);
    }
}
