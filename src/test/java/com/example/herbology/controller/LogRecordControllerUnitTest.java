package com.example.herbology.controller;

import com.example.herbology.dto.LogRecordDto;
import com.example.herbology.model.LogRecord;
import com.example.herbology.service.LogRecordService;
import com.example.herbology.utils.LogGeneratorUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = LogRecordController.class, secure = false)
public class LogRecordControllerUnitTest {
	@MockBean
	private LogRecordService service;

	@Autowired
	private MockMvc mockMvc;

	@InjectMocks
	private LogRecordController controller;

	@Before
	public void init(){
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testAddLogSuccessful() throws Exception {
		// given
		ObjectMapper mapper = new ObjectMapper();
		LogRecordDto recordDto = LogGeneratorUtils.createDto();
		LogRecord record = LogGeneratorUtils.createRecord(1L);

		// when
		when(service.add(any(LogRecordDto.class))).thenReturn(record);

		// actual & then
		mockMvc.perform(MockMvcRequestBuilders.post("/logs/add")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(mapper.writeValueAsString(recordDto)))
				.andExpect(status().is2xxSuccessful())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.data", is("Alfred Jones was HERE!")))
				.andDo(print());
	}
}
