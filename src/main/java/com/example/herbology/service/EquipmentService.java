package com.example.herbology.service;

import com.example.herbology.dto.EquipmentDto;
import com.example.herbology.model.Equipment;

public interface EquipmentService {

    Equipment add(EquipmentDto dto);

    Equipment getById(Long id);
}
