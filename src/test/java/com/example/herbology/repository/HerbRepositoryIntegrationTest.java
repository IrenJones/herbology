package com.example.herbology.repository;

import com.example.herbology.model.Continent;
import com.example.herbology.model.Herb;
import com.example.herbology.utils.HerbGeneratorUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Source: linkedin Spring: Test-Driven Development with JUnit
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class HerbRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private HerbRepository herbRepository;

    @Test
    public void testFindById(){
        // given
        Herb herb = HerbGeneratorUtils.createHerb();
        entityManager.persist(herb);

        // when
        Optional<Herb> result = herbRepository.findById(1L);

        // then
        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), allOf(
                hasProperty("name", equalTo("herb")),
                hasProperty("continent", equalTo(Continent.EUROPE)),
                hasProperty("location", equalTo("location")),
                hasProperty("isDangerous", equalTo(true))
        ));
    }
}
