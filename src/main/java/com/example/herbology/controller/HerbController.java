package com.example.herbology.controller;

import com.example.herbology.dto.HerbDto;
import com.example.herbology.model.Herb;
import com.example.herbology.service.HerbService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/herbs/")
public class HerbController {

    @Autowired
    private HerbService herbService;


    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() {
        return "Hello";
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> testTwo() {
        return new ResponseEntity<>("Darova", HttpStatus.OK);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST) // 201
    @ApiOperation(value = "Herb info", notes = "Help Harry to write all herbs into book!")
    public Herb addHerb(@ApiParam(value = "herb", required = true, name = "Herb Info")
                        @RequestBody HerbDto dto) {
        return herbService.add(dto);
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT) // 200
    @ApiOperation(value = "Herb info", notes = "Help Harry to write all herbs into book!")
    public Herb updateHerb(@ApiParam(value = "herb", required = true, name = "Herb Info")
                           @RequestBody HerbDto dto,
                           @PathVariable(value = "id") Long id) {
        return herbService.update(id, dto);
    }
}
