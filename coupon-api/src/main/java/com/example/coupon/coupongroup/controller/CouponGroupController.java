package com.example.coupon.coupongroup.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.coupon.common.Response;
import com.example.coupon.coupongroup.domain.CouponGroup;
import com.example.coupon.coupongroup.param.CouponGroupParam;
import com.example.coupon.coupongroup.service.CouponGroupService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/coupon-groups")
public class CouponGroupController {
	
	@Autowired
	private CouponGroupService couponGroupService;
	
	/**
	 * 1. 쿠폰 그룹 생성
	 */
	@PostMapping("/{code}")
	public ResponseEntity<Response> createCouponGroup(@RequestHeader("X-USER-ID") @NotNull String issuerId, 
			@PathVariable(name="code") @Pattern(regexp="[a-zA-Z0-9]{2,50}") String code, @RequestBody @Valid CouponGroupParam param){
		
		param.setCode(code);
		param.setIssuerId(issuerId);
		log.info("createCouponGroup param:{}", param);
		
		couponGroupService.createCouponGroup(param);	// 쿠폰 그룹 생성
		CouponGroup couponGroup = couponGroupService.getCouponGroup(param);
		
		return Response.toResponseEntity(couponGroup);
	}
 	
	/**
	 * 2. 쿠폰 그룹 수정
	 */
	@PutMapping("/{code}")
	public ResponseEntity<Response> updateCouponGroup(@RequestHeader("X-USER-ID") @NotNull String issuerId, 
			@PathVariable(name="code") @Pattern(regexp="[a-zA-Z0-9]{2,50}") String code, @RequestBody @Valid CouponGroupParam param){
		
		param.setCode(code);
		param.setIssuerId(issuerId);
		log.info("updateCouponGroup param:{}", param);
		
		couponGroupService.updateCouponGroup(param);	// 쿠폰 그룹 수정
		CouponGroup couponGroup = couponGroupService.getCouponGroup(param);
		
		return Response.toResponseEntity(couponGroup);
	}
	
	/**
	 * 3. 쿠폰 그룹 발행
	 */
	@PostMapping("/{code}/publish")
	public ResponseEntity<Response> publishCouponGroup(@RequestHeader("X-USER-ID") @NotNull String issuerId, 
			@PathVariable(name="code") @Pattern(regexp="[a-zA-Z0-9]{2,50}") String code){
		
		CouponGroupParam param = new CouponGroupParam();
		param.setIssuerId(issuerId);
		param.setCode(code);
		
		couponGroupService.publishCouponGroup(param);	// 쿠폰 그룹 발행
		CouponGroup couponGroup = couponGroupService.getCouponGroup(param);
		
		return Response.toResponseEntity(couponGroup);
	}

	/**
	 * 4. 쿠폰 그룹 비활성화
	 */
	@PostMapping("/{code}/disable")
	public ResponseEntity<Response> disableCouponGroup(@RequestHeader("X-USER-ID") @NotNull String issuerId, 
			@PathVariable(name="code") @Pattern(regexp="[a-zA-Z0-9]{2,50}") String code){
		
		CouponGroupParam param = new CouponGroupParam();
		param.setIssuerId(issuerId);
		param.setCode(code);
		
		couponGroupService.disableCouponGroup(param);	// 쿠폰 그룹 비활성화
		CouponGroup couponGroup = couponGroupService.getCouponGroup(param);
		
		return Response.toResponseEntity(couponGroup);
	}
}
