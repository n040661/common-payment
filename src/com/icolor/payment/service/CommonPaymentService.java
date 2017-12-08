package com.icolor.payment.service;

import javax.servlet.http.HttpServletRequest;

import com.icolor.payment.data.PrePaymentResult;
import com.icolor.payment.enums.PaymentType;


public interface CommonPaymentService
{

	/**
	 * 请求支付
	 * 
	 * @param payType(支付模式，目前有
	 *           UNION,POS,WECHAT)
	 * @param orderCode(订单编号，只能为数字，字母或二者组合，最长不过32位)
	 * @param fee(订单支付金额<br>
	 *           规则：元.角分; <br>
	 *           例： 3.14 表示3元1角4分 , 2.1表示 2元1角 ,5表示5元整 )
	 * @return
	 */
	PrePaymentResult pay(PaymentType payType, String orderCode, String fee);

	/**
	 * 支付回调 获取回调参数，转为自定义支付结果对象
	 * 
	 * @param paymentHandle
	 */
	void callback(HttpServletRequest request, PaymentType payType);

}
