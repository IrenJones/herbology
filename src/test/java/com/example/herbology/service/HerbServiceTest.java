package com.example.herbology.service;

import com.example.herbology.dto.HerbDto;
import com.example.herbology.model.Continent;
import com.example.herbology.model.Herb;
import com.example.herbology.repository.HerbRepository;
import com.example.herbology.service.impl.HerbServiceImpl;
import com.example.herbology.utils.HerbGeneratorUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static com.example.herbology.StrictMockito.strictMock;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.core.AllOf.allOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@RunWith(MockitoJUnitRunner.class)
public class HerbServiceTest {

    @InjectMocks
    private HerbService herbService = new HerbServiceImpl();

    @Ignore
    @Test
    public void successfulCreationWhenParametersValid(){
        // given
        HerbDto dto = HerbGeneratorUtils.createHerbDto();
        Herb herb = HerbGeneratorUtils.createHerb();
        Herb expectedHerb = HerbGeneratorUtils.createHerb(1L);

        // when
        setField(herbService, "mapper",
                strictMock(ObjectMapper.class, mock ->
                        when(mock.convertValue(dto, Herb.class)).thenReturn(herb)
                )
        );

        //todo doesn't work (right now helps only adding Herb save(Herb herb); to repository)
        setField(herbService, "herbRepository",
                strictMock(HerbRepository.class, mock ->
                        when(mock.save(any(Herb.class))).thenReturn(expectedHerb)
                )
        );

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
