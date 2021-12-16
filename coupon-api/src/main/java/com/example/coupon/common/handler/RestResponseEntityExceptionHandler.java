package com.example.coupon.common.handler;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path.Node;
import javax.validation.ValidationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.coupon.common.Response;
import com.example.coupon.common.Constants.ApiErrorCode;
import com.example.coupon.common.exception.ApiException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class RestResponseEntityExceptionHandler{

	@ExceptionHandler(value = { ApiException.class })
	protected ResponseEntity<Response> handleApiException(ApiException e){
		log.error("handleApiException throw Exception : {}", e.getErrorCode());
		return Response.toResponseEntity(e.getErrorCode());
	}
	
    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    protected ResponseEntity<Response> handleMethodArgumentNotValidException (MethodArgumentNotValidException e) {
        return Response.toResponseEntity(ApiErrorCode.INVALID_REQUIRED_BODY_PARAM);
    }
    
    @ExceptionHandler(value = { MissingRequestHeaderException.class })
    protected ResponseEntity<Response> handleMissingRequestHeaderException (MissingRequestHeaderException e) {
        return Response.toResponseEntity(ApiErrorCode.FORBIDDEN);
    }
    
    @ExceptionHandler(value = { ConstraintViolationException.class })
    protected ResponseEntity<Response> handleConstraintViolationException (ConstraintViolationException e) {
    	ConstraintViolation<?> violation = e.getConstraintViolations().iterator().next();

    	for (Node node : violation.getPropertyPath()) {
    	    if(node.getName().equals("code")) {
    	    	 return Response.toResponseEntity(ApiErrorCode.COUPONGROUP_INVALID_CODE);
    	    }
    	}
        return Response.toResponseEntity(ApiErrorCode.BAD_REQUEST);
    }
    
	@ExceptionHandler(value = { Exception.class })
	protected ResponseEntity<Response> handleUntypedException(Exception e) {
		return Response.toResponseEntity(ApiErrorCode.INTERNAL_SERVER_ERROR);
	}
}
