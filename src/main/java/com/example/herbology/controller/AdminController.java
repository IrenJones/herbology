package com.example.herbology.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "Wonderful Admin Controller")
public class AdminController {

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() {
        return "Hello, admin";
    }
}
