package com.micro.service.app.exception;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ApiError> Resourcenotfoundhandler(UserNotFoundException e,HttpServletRequest request)
	{
		ApiError error =new ApiError(new Date(),HttpStatus.NOT_FOUND,request.getRequestURI(),e.getMessage()) ;
		return new ResponseEntity<ApiError>(error,HttpStatus.NOT_FOUND);
	}
}
