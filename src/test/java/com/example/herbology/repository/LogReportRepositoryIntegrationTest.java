package com.example.herbology.repository;

import com.example.herbology.model.LogRecord;
import com.example.herbology.utils.LogGeneratorUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LogReportRepositoryIntegrationTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private LogsRepository logsRepository;

	@Test
	public void testFindById() {
		// given
		LogRecord record = LogGeneratorUtils.createRecord();
		entityManager.persist(record);

		// when
		Optional<LogRecord> result = logsRepository.findById(1L);

		// then
		assertThat(result.isPresent(), is(true));
		assertThat(result.get(), allOf(
				hasProperty("data", equalTo("Alfred Jones was HERE!"))
		));
	}

	@Test
	public void testFindAllOrderedNotLimited() {
		// given
		LogRecord record1 = LogGeneratorUtils.createRecord();
		LogRecord record2 = LogGeneratorUtils.createRecord();
		record2.setData("gooose");
		entityManager.persist(record1);
		entityManager.persist(record2);

		// when
		List<LogRecord> result = logsRepository.findAllOrderedNotLimited();

		// then
		assertThat(result, hasSize(2));
		assertThat(result, hasItem(
				hasProperty("data", equalTo("Alfred Jones was HERE!"))
		));
		assertThat(result, hasItem(
				hasProperty("data", equalTo("gooose"))
		));
	}
}
