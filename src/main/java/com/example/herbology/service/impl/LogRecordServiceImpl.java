package com.example.herbology.service.impl;

import com.example.herbology.dto.LogRecordDto;
import com.example.herbology.model.LogRecord;
import com.example.herbology.repository.CustomLogsRepository;
import com.example.herbology.repository.LogsRepository;
import com.example.herbology.service.LogRecordService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogRecordServiceImpl implements LogRecordService {

	@Autowired
	private CustomLogsRepository customLogsRepository;

	@Autowired
	private LogsRepository logsRepository;

	@Autowired
	private ObjectMapper mapper;

	@Override
	public List<LogRecord> findAll() {
		return customLogsRepository.findAll();
	}

	@Override
	public List<LogRecord> findAllOrderedLimited(Integer n) {
		return customLogsRepository.findAllOrderedLimited(n);
	}

	@Override
	public List<LogRecord> findAllOrderedNotLimited() {
		return logsRepository.findAllOrderedNotLimited();
	}

	@Override
	public LogRecord findById(Long id) {
		return customLogsRepository.findById(id);
	}

	@Override
	public LogRecord add(LogRecordDto dto) {
		return logsRepository.save(
				mapper.convertValue(dto, LogRecord.class)
		);
	}
}
