package com.example.herbology.repository;

import com.example.herbology.model.Equipment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentRepository extends CrudRepository<Equipment, Long> {

    List<Equipment> findAllByAppearanceYear(@Param("Year!") Integer year);
}
