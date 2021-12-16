package com.example.coupon.coupon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.coupon.common.Constants.ApiErrorCode;
import com.example.coupon.common.Constants.CouponGroupStatus;
import com.example.coupon.common.Constants.CouponStatus;
import com.example.coupon.common.exception.ApiException;
import com.example.coupon.coupon.domain.Coupon;
import com.example.coupon.coupon.mapper.CouponMapper;
import com.example.coupon.coupon.param.CouponParam;
import com.example.coupon.coupongroup.domain.CouponGroup;
import com.example.coupon.coupongroup.mapper.CouponGroupMapper;
import com.example.coupon.coupongroup.param.CouponGroupParam;
import com.example.coupon.coupongroup.service.CouponGroupService;
import com.example.coupon.user.param.UserParam;
import com.example.coupon.user.service.UserService;


@Service
@Transactional
public class CouponService{
	
	@Autowired
	CouponMapper couponMapper;
	
	@Autowired
	CouponGroupMapper couponGroupMapper;

	@Autowired
	UserService userService;
	
	@Autowired
	CouponGroupService couponGroupService;

	public void downloadCoupon(CouponParam param) {
		CouponGroupParam couponGroupParam = new CouponGroupParam();
		couponGroupParam.setCode(param.getCode());
		
		CouponGroup couponGroup = couponGroupService.getCouponGroup(couponGroupParam);
		Coupon coupon = couponMapper.selectCoupon(param);
		
		if(coupon == null) {
			if(couponGroup.getStatus() == CouponGroupStatus.PUBLISHED) {
				if(couponGroup.getMaxCount() - couponGroup.getCurrentCount() > 0) {
					couponGroupService.checkCouponGroupValid(couponGroup);	// 쿠폰 유효기간 체크
					
					param.setStatus(CouponStatus.ISSUED);	// 상태  변경
					param.setValidFromDt(couponGroup.getValidFromDt());	// 유효기간은 쿠폰 그룹의 유효기간을 따라감
					param.setValidToDt(couponGroup.getValidToDt());
					param.setName(couponGroup.getName());
					param.setAmount(couponGroup.getAmount());
					couponMapper.insertCoupon(param);	// 쿠폰 생성 
					
					int count = couponGroup.getCurrentCount() + 1;
					couponGroupParam.setCurrentCount(count);
					couponGroupMapper.updateCouponGroup(couponGroupParam);	// 쿠폰 그룹 발급 개수 수정
					
				}else {
					throw new ApiException(ApiErrorCode.COUPON_SOLD_OUT);	// 남은 쿠폰 없음 예외처리
				}
				
			}else {
				throw new ApiException(ApiErrorCode.INVALID_STATUS);	// 상태 이상 예외처리
			}
		}else {
			throw new ApiException(ApiErrorCode.COUPON_DUPLICATE);	// 중복 생성 예외처리
		}
		
		
	}
	
	public void useCoupon(CouponParam param) {
		CouponGroupParam couponGroupParam = new CouponGroupParam();
		couponGroupParam.setCode(param.getCode());
		
		CouponGroup couponGroup = couponGroupService.getCouponGroup(couponGroupParam);
		Coupon coupon = getCoupon(param);
		
		if(coupon.getStatus() == CouponStatus.ISSUED) {
			couponGroupService.checkCouponGroupValid(couponGroup);	// 쿠폰 유효기간 체크
			
			param.setStatus(CouponStatus.USED);		// 상태 변경
			couponMapper.updateCoupon(param);
			
			UserParam userParam = new UserParam();
			userParam.setUserId(param.getUserId());
			userService.addUserPoint(userParam, couponGroup.getAmount());	// 사용자 포인트 증가
		}else {
			throw new ApiException(ApiErrorCode.INVALID_STATUS);	// 상태 이상 예외 처리
		}
	}

	public Coupon getCoupon(CouponParam param) {
		Coupon coupon = couponMapper.selectCoupon(param);
		
		if(coupon == null) {
			throw new ApiException(ApiErrorCode.COUPON_NOT_FOUND);	// 존재하지 않는 쿠폰 예외 처리
		}
		
		return coupon;
	}

	public List<Coupon> getMyCouponList(CouponParam param) {
		return couponMapper.selectMyCouponList(param);
	}
	
}
