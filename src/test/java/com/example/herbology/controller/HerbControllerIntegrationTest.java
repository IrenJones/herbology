package com.example.herbology.controller;

import com.example.herbology.dto.HerbDto;
import com.example.herbology.exception.EntityWthErrorsException;
import com.example.herbology.model.Continent;
import com.example.herbology.model.Herb;
import com.example.herbology.utils.HerbGeneratorUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Working with all layers so it's an integration test
 * Source: linkedin Spring: Test-Driven Development with JUnit
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HerbControllerIntegrationTest {

    @Autowired
    HerbController herbController;

    @Test
    public void testAddHerb() throws Exception {
        HerbDto herb = HerbGeneratorUtils.createHerbDto();

        Herb result = herbController.addHerb(herb);
        assertThat(result, allOf(
                hasProperty("name", equalTo("herb")),
                hasProperty("continent", equalTo(Continent.EUROPE)),
                hasProperty("location", equalTo("location")),
                hasProperty("isDangerous", equalTo(true))
        ));
    }

    @Test(expected = EntityWthErrorsException.class)
    public void testAddHerbWithNull() throws Exception {
        HerbDto herb = null;
        herbController.addHerb(herb);
    }
}
