package com.example.herbology.dto;

import com.example.herbology.model.Continent;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class HerbDto {

    @ApiModelProperty(example = "Mandragora", required = true)
    String name;

    @ApiModelProperty(example = "true", required = true)
    Boolean isDangerous;

    @ApiModelProperty(example = "ASIA", required = true)
    Continent continent;

    @ApiModelProperty(example = "China", required = true)
    String location;
}
