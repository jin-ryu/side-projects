package com.example.coupon.coupongroup.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.coupon.common.Constants.ApiErrorCode;
import com.example.coupon.common.Constants.CouponGroupStatus;
import com.example.coupon.common.exception.ApiException;
import com.example.coupon.coupongroup.domain.CouponGroup;
import com.example.coupon.coupongroup.mapper.CouponGroupMapper;
import com.example.coupon.coupongroup.param.CouponGroupHistoryParam;
import com.example.coupon.coupongroup.param.CouponGroupParam;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CouponGroupService{
	
	@Autowired
	CouponGroupMapper couponGroupMapper;
	
	public CouponGroup getCouponGroup(CouponGroupParam param) {
		log.info("getCouponGroup : {}", param);
		CouponGroup couponGroup = couponGroupMapper.selectCouponGroup(param);
		
		if(couponGroup == null) {
			throw new ApiException(ApiErrorCode.COUPONGROUP_NOT_FOUND);	// 존재하지 않는 쿠폰 그룹 예외 처리
		}
		
		return couponGroup;
	}

	public void createCouponGroup(CouponGroupParam param) {
		CouponGroup couponGroup = couponGroupMapper.selectCouponGroup(param);
		
		if(couponGroup == null) {
			param.setStatus(CouponGroupStatus.CREATED);	// 상태 변경
			log.info("createCouponGroup setStatus : {}", param);
			couponGroupMapper.insertCouponGroup(param);
			createCouponGroupHistory(param);		// 히스토리 기록
		}else {
			throw new ApiException(ApiErrorCode.COUPONGROUP_DUPLICATE);	// 중복 생성 예외처리
		}
	}

	public void updateCouponGroup(CouponGroupParam param) {
		CouponGroup couponGroup = getCouponGroup(param);
		
		CouponGroupStatus status = couponGroup.getStatus(); 
		if(status == CouponGroupStatus.CREATED) {
			couponGroupMapper.updateCouponGroup(param);
			createCouponGroupHistory(param);		// 히스토리 기록
		}else {
			throw new ApiException(ApiErrorCode.INVALID_STATUS);	// 상태 이상 예외처리 
		}
	}

	public void publishCouponGroup(CouponGroupParam param) {
		CouponGroup couponGroup = couponGroupMapper.selectCouponGroup(param);
		
		if(couponGroup.getStatus() == CouponGroupStatus.CREATED) {
			checkCouponGroupValid(couponGroup);	// 쿠폰 유효기간 체크
			
			param.setStatus(CouponGroupStatus.PUBLISHED);	// 상태 변경
			couponGroupMapper.updateCouponGroup(param);
			createCouponGroupHistory(param);		// 히스토리 기록
		}else {
			throw new ApiException(ApiErrorCode.INVALID_STATUS);	// 상태 이상 예외처리 
		}

	}

	public void disableCouponGroup(CouponGroupParam param) {
		CouponGroup couponGroup = getCouponGroup(param);
		
		CouponGroupStatus status = couponGroup.getStatus();
		if(status != CouponGroupStatus.DISABLED) {
			param.setStatus(CouponGroupStatus.DISABLED);	// 상태 변경
			couponGroupMapper.updateCouponGroup(param);
			createCouponGroupHistory(param);		// 히스토리 기록
		}
	}

	public void checkCouponGroupValid(CouponGroup couponGroup) {
		Date validFromDt = couponGroup.getValidFromDt();
		Date validToDt = couponGroup.getValidToDt();
		Date now = new Date();
		
		if(now.after(validToDt) || now.before(validFromDt)){
			throw new ApiException(ApiErrorCode.COUPONGROUP_VALID_OVER);	// 유효기간 지난 쿠폰 예외처리
		}
	}

	public void createCouponGroupHistory(CouponGroupParam param) {
		ObjectMapper mapper = new ObjectMapper();
		CouponGroup couponGroup = getCouponGroup(param);
		
		// history data 생성
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("coupon_group_status", param.getStatus());
		data.put("detail", couponGroup);
		
		String jsonData = null;
		try {
			jsonData = mapper.writeValueAsString(data);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		CouponGroupHistoryParam historyParam = new CouponGroupHistoryParam();
		historyParam.setCouponGroupId(couponGroup.getId());
		historyParam.setUserId(couponGroup.getIssuerId());
		historyParam.setData(jsonData);
		log.info("createCouponGroupHistory data:{}", jsonData);
		
		couponGroupMapper.insertCouponGroupHistory(historyParam);
	}

}
