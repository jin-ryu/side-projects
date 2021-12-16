package com.example.coupon.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.coupon.user.domain.User;
import com.example.coupon.user.mapper.UserMapper;
import com.example.coupon.user.param.UserParam;

@Service
@Transactional
public class UserService{
	
	@Autowired
	UserMapper userMapper;

	public void updateUser(UserParam param) {
		userMapper.updateUser(param);
	}

	public void addUserPoint(UserParam param, int amount) {
		User user = userMapper.getUser(param);
		
		int point = user.getPoint() + amount;	// 새로운 포인트 계산
		param.setPoint(point);
		
		updateUser(param);	// 사용자 수정
	}
}
