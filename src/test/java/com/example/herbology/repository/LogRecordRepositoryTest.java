package com.example.herbology.repository;

import com.example.herbology.model.LogRecord;
import com.example.herbology.utils.LogGeneratorUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class LogRecordRepositoryTest {

	@Autowired
	private LogsRepository logsRepository;

	@Test
	public void testSaveAndGet(){
		logsRepository.save(LogGeneratorUtils.createRecord());
		LogRecord logRecord = logsRepository.findById(1L).get();
		assertThat(logRecord.getData()).isEqualTo("Alfred Jones was HERE!");
	}
}
