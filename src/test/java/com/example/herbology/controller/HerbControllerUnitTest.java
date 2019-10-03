package com.example.herbology.controller;

import com.example.herbology.dto.HerbDto;
import com.example.herbology.model.Herb;
import com.example.herbology.service.HerbService;
import com.example.herbology.utils.HerbGeneratorUtils;
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
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = HerbController.class, secure = false)
public class HerbControllerUnitTest {

    @MockBean
    private HerbService service;

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private HerbController herbController;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddHerbSuccessful() throws Exception {
        // given
        ObjectMapper mapper = new ObjectMapper();
        HerbDto herbDto = HerbGeneratorUtils.createHerbDto();
        Herb herb = HerbGeneratorUtils.createHerb(1L);

        // when
        when(service.add(any(HerbDto.class))).thenReturn(herb);

        // actual & then
        mockMvc.perform(MockMvcRequestBuilders.post("/herbs/add")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(herbDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.location", is("location")))
                .andExpect(jsonPath("$.continent", is("EUROPE")))
                .andExpect(jsonPath("$.isDangerous", is(true)))
                .andExpect(jsonPath("$.name", is("herb")))
                .andDo(print());
    }

    @Test
    public void testAddHerbWithValidationErrors() throws Exception {
        // given
        ObjectMapper mapper = new ObjectMapper();
        HerbDto herbDto = HerbGeneratorUtils.createHerbDto();

        // actual & then
        mockMvc.perform(MockMvcRequestBuilders.post("/herbs/add")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(herbDto)))
                .andExpect(status().reason(is(nullValue())))
                .andReturn();
    }
}
