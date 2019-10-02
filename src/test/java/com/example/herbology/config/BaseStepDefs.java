package com.example.herbology.config;

import com.example.herbology.HerbologyApplication;
import cucumber.api.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertEquals;

@WebAppConfiguration
@ContextConfiguration(classes = HerbologyApplication.class, loader = SpringBootContextLoader.class)
public class BaseStepDefs {
    @Autowired
    MockMvcStepDefs mockMvcStepDefs;

    @Then("^the response status code is (\\d+)$")
    public void theResponseStatusCodeIs(Integer code) {
        Integer currentStatusCode = mockMvcStepDefs.getLatestResponseStatus();
        String message = "status code is incorrect : " + mockMvcStepDefs.getLatestResponseContentAsString();
        assertEquals(message, code, currentStatusCode);
    }
}
