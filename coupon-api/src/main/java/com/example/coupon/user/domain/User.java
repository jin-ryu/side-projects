package com.example.coupon.user.domain;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NonNull;

@Data
public class User {
	
	private String id;
	private String name;
	private int point;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
}
