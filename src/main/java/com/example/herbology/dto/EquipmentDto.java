package com.example.herbology.dto;

import com.example.herbology.model.Continent;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
public class EquipmentDto {

    @Size(max = 256, message = "Cannot have more than 256 characters")
    @ApiModelProperty(example = "Earmuffs", required = true)
    String name;

    @ApiModelProperty(example = "Protect the ears and shut out sound", required = false)
    String destination;

    @Min(1) @Max(7)
    @ApiModelProperty(example = "3", required = true, name = "Year of studying when it requires")
    Integer appearanceYear;
}
