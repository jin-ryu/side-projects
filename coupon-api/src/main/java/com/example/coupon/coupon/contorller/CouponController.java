package com.example.coupon.coupon.contorller;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.coupon.common.Response;
import com.example.coupon.coupon.domain.Coupon;
import com.example.coupon.coupon.param.CouponParam;
import com.example.coupon.coupon.service.CouponService;
import com.example.coupon.coupon.service.CouponService;

import lombok.extern.slf4j.Slf4j;

@Validated
@RestController
@RequestMapping("/api/coupons")
public class CouponController {
	
	@Autowired
	CouponService couponService;
	
	/**
	 * 5. 쿠폰 다운로드
	 */
	@PostMapping("/{code}/download")
	public ResponseEntity<Response> downloadCoupon(@RequestHeader("X-USER-ID") @NotNull String userId,
			@PathVariable(name="code") @Pattern(regexp="[a-zA-Z0-9]{2,50}") String code){
		
		CouponParam param = new CouponParam();
		param.setCode(code);
		param.setUserId(userId);
		
		couponService.downloadCoupon(param); 	// 쿠폰 다운로드
		Coupon coupon = couponService.getCoupon(param);
		
		return Response.toResponseEntity(coupon);
	}

	/**
	 * 6. 쿠폰 사용
	 */
	@PostMapping("/{code}/use")
	public ResponseEntity<Response> useCoupon(@RequestHeader("X-USER-ID") @NotNull String userId, 
			@PathVariable(name="code") @Pattern(regexp="[a-zA-Z0-9]{2,50}") String code){
		
		CouponParam param = new CouponParam();
		param.setCode(code);
		param.setUserId(userId);
		
		couponService.useCoupon(param); 	// 쿠폰 사용
		Coupon coupon = couponService.getCoupon(param);
		
		return Response.toResponseEntity(coupon);
	}

	/**
	 * 7. 쿠폰 목록 조회
	 */ 
	@GetMapping
	public ResponseEntity<Response> getMyCouponList(@RequestHeader("X-USER-ID") @NotNull String userId, 
			@RequestParam(name="page", defaultValue="0") @Min(0) @Max(Long.MAX_VALUE) Long page, @RequestParam(name="size", defaultValue="5") @Min(1) @Max(5) Integer size){
		
		CouponParam param = new CouponParam();
		param.setUserId(userId);
		param.setPage(page);
		param.setSize(size);
		
		List<Coupon> couponList = couponService.getMyCouponList(param);	// 보유 쿠폰 목혹 조회
		
		return Response.toResponseEntity(couponList);
	}
	
}
