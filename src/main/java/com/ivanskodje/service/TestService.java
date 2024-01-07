package com.ivanskodje.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TestService
{
    @Value("${application.message}")
    private String messageFromApplicationYml;

    public void doSomething()
    {
        System.out.println("Doing something in TestService.java: " + messageFromApplicationYml);
    }
}