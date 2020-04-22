package com.example.herbology.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
public class LogRecordDto {

	@NotNull
	@ApiModelProperty(name = "Wonderful data to store", example = "Wonderful day!", required = true)
	String data;
}
