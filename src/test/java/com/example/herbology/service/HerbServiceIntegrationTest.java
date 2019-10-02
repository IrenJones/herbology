package com.example.herbology.service;

import com.example.herbology.dto.HerbDto;
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
 * Working with data layer too so it's an integration test
 * Source: linkedin Spring: Test-Driven Development with JUnit
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HerbServiceIntegrationTest {

    @Autowired
    private HerbService herbService;

    @Test
    public void successfulCreationWhenParametersValid(){
        // given
        HerbDto dto = HerbGeneratorUtils.createHerbDto();
        Herb herb = HerbGeneratorUtils.createHerb();
        Herb expectedHerb = HerbGeneratorUtils.createHerb(1L);

        // action
        Herb result = herbService.add(dto);

        // then
        assertThat(result, allOf(
                hasProperty("name", equalTo("herb")),
                hasProperty("continent", equalTo(Continent.EUROPE)),
                hasProperty("location", equalTo("location")),
                hasProperty("isDangerous", equalTo(true))
        ));
    }
}
