package com.example.herbology.service;

import com.example.herbology.dto.LogRecordDto;
import com.example.herbology.model.LogRecord;
import com.example.herbology.utils.LogGeneratorUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LogRecordServiceIntegrationTest {

	@Autowired
	private LogRecordService recordService;

	@Test
	public void successfulCreationWhenParametersValid(){
		// given
		LogRecordDto dto = LogGeneratorUtils.createDto();

		// action
		LogRecord result = recordService.add(dto);

		// then
		assertThat(result, allOf(
				hasProperty("data", equalTo("Alfred Jones was HERE!"))
		));
	}

	@Sql("classpath:dataset/log-repository.sql")
	@Test
	public void successfulFindById(){
		// action
		LogRecord result = recordService.findById(1L);

		// then
		assertThat(result, allOf(
				hasProperty("data", equalTo("gcp"))
		));
	}
}
