package com.example.coupon.coupongroup.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doReturn;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.jdbc.Sql;

import com.example.coupon.coupongroup.domain.CouponGroup;
import com.example.coupon.coupongroup.mapper.CouponGroupMapper;
import com.example.coupon.coupongroup.param.CouponGroupParam;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Sql({"classpath:schema-h2.sql", "classpath:data-h2.sql"})
@DisplayName("CouponGroupService 테스트")
public class CouponGroupServiceTest {
	
	@InjectMocks
	private CouponGroupService couponGroupService;
	
	@Mock
	private CouponGroupMapper couponGroupMapper;
	
	private AutoCloseable closeable;
	
	@BeforeEach
	void initService() {
		closeable = MockitoAnnotations.openMocks(this);
	}
	
    @AfterEach
    void closeService() throws Exception {
        closeable.close();
    }
    
	 
	@DisplayName("쿠폰 그룹 조회 성공")
	@Test
	void getCouponGroupSuccess() {
		// given
		final CouponGroupParam param = new CouponGroupParam();
		param.setCode("CP1000");
		
		doReturn(CouponGroup.builder()
				.name("1000 포인트 쿠폰")
				.issuerId("issuer1")
				.code("CP1000")
				.build()
				)
		.when(couponGroupMapper)
		.selectCouponGroup(param);
	
		// when
		final CouponGroup result = couponGroupService.getCouponGroup(param);
		log.info("result : {}", result);

		// then
		assertEquals("CP1000", result.getCode());
		assertEquals("issuer1", result.getIssuerId());
		assertEquals("1000 포인트 쿠폰", result.getName());
	}	
}
