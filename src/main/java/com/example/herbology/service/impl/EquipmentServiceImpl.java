package com.example.herbology.service.impl;

import com.example.herbology.dto.EquipmentDto;
import com.example.herbology.model.Equipment;
import com.example.herbology.repository.EquipmentRepository;
import com.example.herbology.service.EquipmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Objects;

@Slf4j
@Service
public class EquipmentServiceImpl implements EquipmentService {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public Equipment add(EquipmentDto dto) {
        Equipment equipment = mapper.convertValue(dto, Equipment.class);
        return equipmentRepository.save(equipment);
    }

    @Override
    public Equipment getById(Long id) {
        if(Objects.nonNull(id) && equipmentRepository.existsById(id)) {
            return equipmentRepository.findById(id).get();
        }
        else {
            throw new EntityNotFoundException("Equipment id " + id);
        }
    }
}
