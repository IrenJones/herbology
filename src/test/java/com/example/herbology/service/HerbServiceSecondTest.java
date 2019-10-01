package com.example.herbology.service;

import com.example.herbology.HerbologyApplicationTests;
import com.example.herbology.dto.HerbDto;
import com.example.herbology.model.Continent;
import com.example.herbology.model.Herb;
import com.example.herbology.repository.HerbRepository;
import com.example.herbology.service.impl.HerbServiceImpl;
import com.example.herbology.utils.HerbGeneratorUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.core.AllOf.allOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HerbServiceSecondTest {

    @Mock
    private ObjectMapper mapper;

    @Mock
    private HerbRepository herbRepository;

    @InjectMocks
    private HerbService herbService = new HerbServiceImpl();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void successfulCreationWhenParametersValid(){
        // given
        HerbDto dto = HerbGeneratorUtils.createHerbDto();
        Herb herb = HerbGeneratorUtils.createHerb();
        Herb expectedHerb = HerbGeneratorUtils.createHerb(1L);

        // when
        when(mapper.convertValue(dto, Herb.class)).thenReturn(herb);
        when(herbRepository.save(any(Herb.class))).thenReturn(expectedHerb);

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
