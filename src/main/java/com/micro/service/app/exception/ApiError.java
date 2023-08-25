package com.micro.service.app.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {
	
	private Date timestamp;
	private HttpStatus status;
	private String path;
	private String error;

}
