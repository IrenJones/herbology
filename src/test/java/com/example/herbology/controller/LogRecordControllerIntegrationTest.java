package com.example.herbology.controller;

import com.example.herbology.dto.LogRecordDto;
import com.example.herbology.model.LogRecord;
import com.example.herbology.utils.LogGeneratorUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LogRecordControllerIntegrationTest {

	@Autowired
	LogRecordController controller;

	@Test
	public void testAddLog(){
		LogRecordDto dto = LogGeneratorUtils.createDto();
		ResponseEntity<LogRecord> result = controller.add(dto);
		assertThat(result.getBody(), allOf(
				hasProperty("data", equalTo("Alfred Jones was HERE!"))
		));
	}
}
