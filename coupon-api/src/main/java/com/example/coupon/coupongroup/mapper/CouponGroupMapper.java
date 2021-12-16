package com.example.coupon.coupongroup.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.coupon.coupongroup.domain.CouponGroup;
import com.example.coupon.coupongroup.param.CouponGroupHistoryParam;
import com.example.coupon.coupongroup.param.CouponGroupParam;

@Mapper
public interface CouponGroupMapper {

	/**
	 * 쿠폰 그룹 생성
	 * @param param
	 */
	void insertCouponGroup(CouponGroupParam param);
	
	/**
	 * 쿠폰 그룹 수정
	 * @param param
	 */
	void updateCouponGroup(CouponGroupParam param);
	
	/**
	 * 쿠폰 그룹 조회
	 * @param param
	 * @return
	 */
	CouponGroup selectCouponGroup(CouponGroupParam param);
	
	/**
	 * 쿠폰 그룹 변경 이력 생성
	 * @param historyParam
	 */
	void insertCouponGroupHistory(CouponGroupHistoryParam historyParam);
}
