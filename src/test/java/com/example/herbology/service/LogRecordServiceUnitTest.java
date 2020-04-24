package com.example.herbology.service;

import com.example.herbology.dto.LogRecordDto;
import com.example.herbology.model.LogRecord;
import com.example.herbology.repository.CustomLogsRepository;
import com.example.herbology.repository.LogsRepository;
import com.example.herbology.service.impl.LogRecordServiceImpl;
import com.example.herbology.utils.LogGeneratorUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.core.AllOf.allOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest()
public class LogRecordServiceUnitTest {
	@Mock
	private ObjectMapper mapper;

	@Mock
	private LogsRepository logsRepository;

	@Mock
	private CustomLogsRepository customLogsRepository;

	@InjectMocks
	private LogRecordService service = new LogRecordServiceImpl();

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void successfulCreationWhenParametersValid(){
		// given
		LogRecord record = LogGeneratorUtils.createRecord();
		LogRecord expectedRecord = LogGeneratorUtils.createRecord(1L);
		LogRecordDto dto = LogGeneratorUtils.createDto();

		// when
		when(mapper.convertValue(dto, LogRecord.class))
				.thenReturn(record);
		when(logsRepository.save(any(LogRecord.class)))
				.thenReturn(expectedRecord);

		// action
		LogRecord result = service.add(dto);

		// then
		assertThat(result, allOf(
				hasProperty("data", equalTo("Alfred Jones was HERE!"))
		));
	}

	@Test
	public void successfulFindById(){
		// given
		LogRecord expectedRecord = LogGeneratorUtils.createRecord(1L);

		// when
		when(customLogsRepository.findById(anyLong()))
				.thenReturn(expectedRecord);

		// action
		LogRecord result = service.findById(1L);

		// then
		assertThat(result, allOf(
				hasProperty("data", equalTo("Alfred Jones was HERE!"))
		));
	}
}
