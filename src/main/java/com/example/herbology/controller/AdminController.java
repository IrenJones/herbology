package com.example.herbology.controller;

import io.swagger.annotations.Api;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/admin/")
@Api(value = "Wonderful Admin Controller")
public class AdminController {

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ADMIN')")
    public String test() {
        return "Hello, admin";
    }
}
