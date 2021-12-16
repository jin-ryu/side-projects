package com.example.coupon.coupongroup.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CouponGroupHistory {

	private Long id;
	private Long couponGroupId;
	private String userId;
	private String data;
	private LocalDateTime createdAt;
	
}
