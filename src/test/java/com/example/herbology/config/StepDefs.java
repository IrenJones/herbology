package com.example.herbology.config;

import io.cucumber.java.en.And;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class StepDefs {

    @Autowired
    private MockMvcStepDefs mockMvcStepDefs;
    @Autowired
    private AliasRegistry aliasRegistry;

    @And("^the client receives answer (.+)$")
    public void the_client_receives_test_answer(String s) throws Throwable {
        String actualS = mockMvcStepDefs.getLatestResponseContentAsString();
        assertThat(actualS, is("Hello"));
    }
}
