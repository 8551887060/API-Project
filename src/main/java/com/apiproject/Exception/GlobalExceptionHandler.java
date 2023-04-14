package com.apiproject.Exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.apiproject.payload.ErrorDetails;
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	//Specific Exception Handling
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorDetails> HandleResourceNotFoundException(ResourceNotFoundException exception,
			      WebRequest webRequest
			){
		ErrorDetails error= new ErrorDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
	
	//Global Exception Handling
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> HandleGlobalException(Exception exception, WebRequest webRequest){
		
		ErrorDetails error= new ErrorDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));
		return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
