package com.example.coupon.user.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.coupon.user.domain.User;
import com.example.coupon.user.param.UserParam;

@Mapper
public interface UserMapper {
	
	/**
	 * 사용자 조회
	 * @param param
	 * @return
	 */
	User getUser(UserParam param);

	/**
	 * 사용자 수정
	 * @param param
	 */
	void updateUser(UserParam param);
}
