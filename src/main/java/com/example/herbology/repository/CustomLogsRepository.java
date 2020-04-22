package com.example.herbology.repository;

import com.example.herbology.model.LogRecord;

import java.util.List;

public interface CustomLogsRepository {
	List<LogRecord> findAll();
	List<LogRecord> findAllOrderedLimited(Integer n);
	LogRecord findById(Long id);
}
