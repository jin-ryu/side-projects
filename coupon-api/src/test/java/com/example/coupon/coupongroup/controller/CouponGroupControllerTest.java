package com.example.coupon.coupongroup.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.coupon.coupongroup.service.CouponGroupService;

@ExtendWith(MockitoExtension.class)
public class CouponGroupControllerTest {
	
	@InjectMocks
	private CouponGroupController couponGroupController;
	
	@Mock
	private CouponGroupService couponGroupService;
	
	private MockMvc mockMvc;    
	
	@BeforeEach    
	public void init() {
		mockMvc = MockMvcBuilders.standaloneSetup(couponGroupController).build();   
	}
	
	@DisplayName("쿠폰 그룹 생성 성공")
	@Test
	void createCouponGroupSuccess() {
		// given
		
		// when
		
		// then
	}
	
}
