package com.example.coupon.coupon.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.coupon.coupon.domain.Coupon;
import com.example.coupon.coupon.param.CouponParam;

@Mapper
public interface CouponMapper {
	
	/**
	 * 쿠폰 생성
	 * @param param
	 */
	void insertCoupon(CouponParam param);
	
	/**
	 * 쿠폰 수정
	 * @param param
	 */
	void updateCoupon(CouponParam param);
	
	/**
	 * 쿠폰 조회
	 * @param param
	 * @return
	 */
	Coupon selectCoupon(CouponParam param);
	
	/**
	 * 보유 쿠폰 목록 조회
	 * @param param
	 * @return
	 */
	List<Coupon> selectMyCouponList(CouponParam param);

}
