package com.example.herbology.service;

import com.example.herbology.dto.LogRecordDto;
import com.example.herbology.model.LogRecord;

import java.util.List;

public interface LogRecordService {
	List<LogRecord> findAll();
	List<LogRecord> findAllOrderedLimited(Integer n);
	List<LogRecord> findAllOrderedNotLimited();
	LogRecord findById(Long id);
	LogRecord add(LogRecordDto dto);
}
