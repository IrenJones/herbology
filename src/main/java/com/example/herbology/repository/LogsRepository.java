package com.example.herbology.repository;

import com.example.herbology.model.LogRecord;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogsRepository extends CrudRepository<LogRecord, Long> {

	@Query("SELECT l FROM LogRecord l " +
			"ORDER BY id DESC")
	List<LogRecord> findAllOrderedNotLimited();
}
