package com.example.coupon.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.example.coupon.common.Constants.ApiErrorCode;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class Response {
	
	private final boolean success;
	private final Object response;
	private final Object error;
	
	/**
	 * 정상 처리
	 * @param data
	 * @return
	 */
	public static ResponseEntity<Response> toResponseEntity(Object data) {
		Response response = Response.builder()
				.success(true)
            	.response(data)
            	.error(null)
            	.build();
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	 }
	
	/**
	 * API 에러 처리
	 * @param status
	 * @param message
	 * @return
	 */
	public static ResponseEntity<Response> toResponseEntity(HttpStatus status, String message) {
		Response response = Response.builder()
				.success(false)
            	.response(null)
            	.error(ErrorResponse.builder()
            			.status(status.value())
            			.message(message)
            			.build()
            			)
            	.build();
		
		return ResponseEntity.status(status).body(response);
	}
	
	/**
	 * API 에러 처리 (ApiErrorCode)
	 * @param errorCode
	 * @return
	 */
	public static ResponseEntity<Response> toResponseEntity(ApiErrorCode errorCode) {
		return Response.toResponseEntity(errorCode.getStatus(), errorCode.getMessage());
	}	
}
