package com.example.herbology.repository;

import com.example.herbology.model.Herb;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HerbRepository extends CrudRepository<Herb, Long> {
}
