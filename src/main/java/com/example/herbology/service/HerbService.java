package com.example.herbology.service;

import com.example.herbology.dto.HerbDto;
import com.example.herbology.model.Herb;

public interface HerbService {

    Herb add(HerbDto dto);

    void update();

    void delete();

    void find();
}
