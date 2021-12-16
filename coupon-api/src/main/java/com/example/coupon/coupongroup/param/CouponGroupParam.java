package com.example.coupon.coupongroup.param;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.coupon.common.Constants.CouponGroupStatus;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;
import lombok.Data;

@Data
@JsonNaming(value = SnakeCaseStrategy.class)
public class CouponGroupParam {
	
	private String code;
	
	private String issuerId;
	
	private String userId;
	
	private CouponGroupStatus status;
	
	@NotNull
	private String name;
	
	@NotNull
	private Integer amount;
	
	@NotNull
	private Integer maxCount;
	
	private Integer currentCount;
	
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
	private Date validFromDt;
	
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
	private Date validToDt;
}
