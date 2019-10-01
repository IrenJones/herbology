package com.example.herbology.controller;

import com.example.herbology.model.Continent;
import com.example.herbology.model.Herb;
import com.example.herbology.service.HerbService;
import com.example.herbology.utils.HerbGeneratorUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@WebMvcTest(HerbController.class)
public class HerbControllerTest {

    @MockBean
    private HerbService service;

    @Autowired
    private MockMvc mockMvc;
    @Test
    public void testReturn200() throws Exception {
        given(service.getById(any(Long.class))).willReturn(HerbGeneratorUtils.createHerb(3L));
        mockMvc.perform(MockMvcRequestBuilders.get("/herbs/3")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.location", is("location")))
                .andExpect(jsonPath("$.continent", is("EUROPE")))
                .andExpect(jsonPath("$.isDangerous", is(true)))
                .andExpect(jsonPath("$.name", is("herb")))
                .andDo(print());
        verify(service, times(1)).getById(Matchers.any(Long.class));
        verifyNoMoreInteractions(service);
    }
}
