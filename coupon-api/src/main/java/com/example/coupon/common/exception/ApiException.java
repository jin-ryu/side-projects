package com.example.coupon.common.exception;

import com.example.coupon.common.Constants.ApiErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * API 에러 처리 Exception
 * @author ann09
 *
 */
@Getter
@AllArgsConstructor
public class ApiException extends RuntimeException{
	
	private ApiErrorCode errorCode;
}
