package com.example.herbology.dto;

import com.example.herbology.model.Continent;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class HerbDto {

    @NotNull
    @ApiModelProperty(example = "Mandragora", required = true)
    String name;

    @ApiModelProperty(example = "true", required = true)
    Boolean isDangerous;

    @ApiModelProperty(example = "ASIA", required = true)
    Continent continent;

    @ApiModelProperty(example = "China", required = true)
    String location;
}
