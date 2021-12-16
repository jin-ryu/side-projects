package com.example.coupon.coupon.param;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.coupon.common.Constants.CouponStatus;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(value = SnakeCaseStrategy.class)
public class CouponParam {
	
	private String code;
	private String userId;
	private String name;
	private CouponStatus status;
	private Integer amount;
	private Long page;
	private Integer size;
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
	private Date validFromDt;
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
	private Date validToDt;
	
}
