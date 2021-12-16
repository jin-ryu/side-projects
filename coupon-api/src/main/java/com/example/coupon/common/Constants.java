package com.example.coupon.common;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeException;
import org.apache.ibatis.type.TypeHandler;
import org.springframework.http.HttpStatus;

import com.example.coupon.common.handler.CodeEnum;
import com.example.coupon.common.handler.CodeEnumTypeHandler;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

public class Constants {
	
	/**
	 * API 에러코드
	 */
	@Getter
	@AllArgsConstructor
	public enum ApiErrorCode{
		
		/* 400 BAD_REQUEST */
		BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 문법으로 인하여 서버가 요청을 이해할 수 없습니다."),
		INVALID_REQUIRED_BODY_PARAM(HttpStatus.BAD_REQUEST, "body paramter를 확인해주세요."),
		COUPONGROUP_INVALID_CODE(HttpStatus.BAD_REQUEST, "쿠폰 코드는 2 ~ 50 자리의 영숫자 형식만 입력 가능합니다."),
		
		/* 403 FORBIDDEN */
		FORBIDDEN(HttpStatus.FORBIDDEN, "클라이언트가 콘텐츠에 접근할 권리를 가지고 있지 않습니다."),
		INVALID_STATUS(HttpStatus.FORBIDDEN, "변경할 수 없는 상태입니다. 쿠폰 그룹 상태를 확인해 주세요"),
		COUPONGROUP_VALID_OVER(HttpStatus.FORBIDDEN, "유효기간이 지난 쿠폰 그룹입니다."),
		COUPON_SOLD_OUT(HttpStatus.FORBIDDEN, "남은 쿠폰이 없습니다."),
		COUPONGROUP_DUPLICATE(HttpStatus.FORBIDDEN, "중복된 쿠폰 그룹입니다."),
		COUPON_DUPLICATE(HttpStatus.FORBIDDEN, "중복된 쿠폰 입니다."),
		
		/* 404 NOT_FOUND */
		NOT_FOUND(HttpStatus.NOT_FOUND, "요청받은 리소스를 찾을 수 없습니다."),
		COUPONGROUP_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않은 쿠폰 그룹입니다."),
		COUPON_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 쿠폰입니다."),
		
		/* 500 INTERNAL_SERVER_ERROR */
		INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버가 처리 방법을 모르는 상황이 발생했습니다.");
	
		private HttpStatus status;
		private String message;
	}
	
	/**
	 * 쿠폰 그룸 상태 공통코드
	 */
	public enum CouponGroupStatus implements CodeEnum{
		CREATED, 
		PUBLISHED, 
		DISABLED;

		@Override
		@JsonValue
		public String getCode() {
			// TODO Auto-generated method stub
			return this.toString();
		}
		
		@MappedTypes(CouponGroupStatus.class)
	    public static class TypeHandler extends CodeEnumTypeHandler<CouponGroupStatus> {
			public TypeHandler() {
				super(CouponGroupStatus.class);
			}
		}
	};
	
	/**
	 * 쿠폰 상태 공통코드
	 */
	public enum CouponStatus implements CodeEnum{
		ISSUED, 
		USED, 
		EXPIRED;

		@Override
		public String getCode() {
			// TODO Auto-generated method stub
			return this.toString();
		}
		
		@MappedTypes(CouponStatus.class)
	    public static class TypeHandler extends CodeEnumTypeHandler<CouponStatus> {
			public TypeHandler() {
				super(CouponStatus.class);
			}
		}
	};

}