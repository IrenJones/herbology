package com.example.herbology.controller;

import com.example.herbology.dto.LogRecordDto;
import com.example.herbology.model.LogRecord;
import com.example.herbology.service.LogRecordService;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/logs/")
@RequiredArgsConstructor
public class LogRecordController {

	private final LogRecordService service;

	@PostMapping(value = "/add")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<LogRecord> add(@ApiParam(value = "record data", required = true, name = "Info")
										 @Valid @RequestBody LogRecordDto dto) {
		return new ResponseEntity<>(service.add(dto), HttpStatus.CREATED);
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<LogRecord> get(@PathVariable(value = "id") Long id) {
		return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
	}

	@GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<LogRecord>> getAll() {
		return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
	}

	@GetMapping(value = "/list_ordered_limited", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<LogRecord>> getAllOrderedLimited(@RequestParam(value = "n") Integer n) {
		return new ResponseEntity<>(service.findAllOrderedLimited(n), HttpStatus.OK);
	}

	@GetMapping(value = "/list_ordered", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<LogRecord>> getAllOrdered() {
		return new ResponseEntity<>(service.findAllOrderedNotLimited(), HttpStatus.OK);
	}
}
