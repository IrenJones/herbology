package com.example.herbology.utils;

import com.example.herbology.dto.HerbDto;
import com.example.herbology.model.Continent;
import com.example.herbology.model.Herb;
import lombok.experimental.UtilityClass;

@UtilityClass
public class HerbGeneratorUtils {

    public static Herb createHerb(){
        return new Herb()
                .setContinent(Continent.EUROPE)
                .setIsDangerous(true)
                .setLocation("location")
                .setName("herb");
    }

    public static Herb createHerb(Long id){
        return createHerb().setId(id);
    }

    public static HerbDto createHerbDto(){
        return new HerbDto()
                .setContinent(Continent.EUROPE)
                .setIsDangerous(true)
                .setLocation("location")
                .setName("herb");
    }

    public static HerbDto createErrorHerbDto(){
        return new HerbDto()
                .setContinent(Continent.EUROPE)
                .setIsDangerous(true)
                .setLocation("location");
    }
}
