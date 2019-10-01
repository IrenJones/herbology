package com.example.herbology.service.impl;

import com.example.herbology.dto.HerbDto;
import com.example.herbology.model.Herb;
import com.example.herbology.repository.HerbRepository;
import com.example.herbology.service.HerbService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Objects;

@Slf4j
@Service
public class HerbServiceImpl implements HerbService {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private HerbRepository herbRepository;

    @Override
    public Herb add(HerbDto dto) {
        Herb herb = mapper.convertValue(dto, Herb.class);
        return herbRepository.save(herb);
    }

    @Override
    public Herb update(Long id, HerbDto dto) {
        if(Objects.nonNull(id) && herbRepository.existsById(id)){
            Herb herb = mapper.convertValue(dto, Herb.class);
            herb.setId(id);
            return herbRepository.save(herb);
        }
        throw new EntityNotFoundException("Herb id " + id);
    }

    @Override
    public void delete() {

    }

    @Override
    public Herb getById(Long id) {
        if(Objects.nonNull(id) && herbRepository.existsById(id)) {
            return herbRepository.findById(id).get();
        }
        else {
            throw new EntityNotFoundException("Herb id " + id);
        }
    }
}
