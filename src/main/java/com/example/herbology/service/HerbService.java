package com.example.herbology.service;

import com.example.herbology.dto.HerbDto;
import com.example.herbology.model.Herb;

public interface HerbService {

    Herb add(HerbDto dto);

    Herb update(Long id, HerbDto dto);

    void delete();

    void find();
}
