package com.example.herbology.dto;

import com.example.herbology.model.Continent;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EquipmentDto {

    @ApiModelProperty(example = "Earmuffs", required = true)
    String name;

    @ApiModelProperty(example = "Protect the ears and shut out sound", required = false)
    String destination;

    @ApiModelProperty(example = "3", required = true, name = "Year of studying when it requires")
    Integer appearanceYear;
}
