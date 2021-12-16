package com.example.coupon.coupongroup.param;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(value = SnakeCaseStrategy.class)
public class CouponGroupHistoryParam {
	
	private Long couponGroupId;
	private String userId;
	private String data;
}
