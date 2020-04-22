package com.example.herbology.repository;

import com.example.herbology.model.LogRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class CustomLogsRepositoryImpl implements CustomLogsRepository {

	@PersistenceContext
	private final EntityManager entityManager;

	@Override
	public List<LogRecord> findAll() {
		TypedQuery<LogRecord> query = entityManager.createQuery(
				"SELECT record FROM LogRecord record", LogRecord.class);
		return query
				.getResultList();
	}

	@Override
	public List<LogRecord> findAllOrderedLimited(Integer n) {
		return createQuery("SELECT record FROM LogRecord record ORDER BY id desc", n)
				.getResultList();
	}

	@Override
	public LogRecord findById(Long id) {
		TypedQuery<LogRecord> query = entityManager.createQuery(
				"SELECT record FROM LogRecord record where record.id = :id", LogRecord.class);
		return query.setParameter("id", id)
				.getSingleResult();
	}

	private TypedQuery<LogRecord> createQuery(String jpaQuery, Integer n) {
		n = Objects.isNull(n)
				? 0
				: n;
		return entityManager
				.createQuery(jpaQuery, LogRecord.class)
				.setMaxResults(n);
	}
}
