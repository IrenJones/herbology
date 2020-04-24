package com.example.herbology.utils;

import com.example.herbology.dto.LogRecordDto;
import com.example.herbology.model.LogRecord;
import lombok.experimental.UtilityClass;

@UtilityClass
public class LogGeneratorUtils {
	public static LogRecord createRecord(){
		return new LogRecord()
				.setData("Alfred Jones was HERE!");
	}

	public static LogRecord createRecord(Long id){
		return createRecord()
				.setId(id);
	}

	public static LogRecordDto createDto(){
		return new LogRecordDto()
				.setData("Alfred Jones was HERE!");
	}
}
