package com.example.herbology.repository;

import com.example.herbology.model.Herb;
import com.example.herbology.utils.HerbGeneratorUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
public class HerbRepositoryTest {

    @Autowired
    private HerbRepository herbRepository;

    @Test
    public void testSaveAndGet(){
        herbRepository.save(HerbGeneratorUtils.createHerb());
        Herb herb = herbRepository.findById(1L).get();
        assertThat(herb.getName()).isEqualTo("herb");
    }
}
