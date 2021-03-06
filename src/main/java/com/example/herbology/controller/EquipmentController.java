package com.example.herbology.controller;

import com.example.herbology.dto.EquipmentDto;
import com.example.herbology.dto.HerbDto;
import com.example.herbology.model.Equipment;
import com.example.herbology.model.Herb;
import com.example.herbology.service.EquipmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/equipment/")
@Api(value = "Wonderful Controller")
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "Tool info", notes = "Help Harry to add necessary tools for each year!")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Equipment> add(@ApiParam(value = "tool", required = true, name = "Tool Info")
                                         @Valid @RequestBody EquipmentDto dto) {
        return new ResponseEntity<>(equipmentService.add(dto), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Equipment> get(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(equipmentService.getById(id), HttpStatus.OK);
    }
}
