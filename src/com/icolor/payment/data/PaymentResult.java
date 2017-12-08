package com.icolor.payment.data;

import com.icolor.payment.enums.PaymentType;

public class PaymentResult
{
	//支付结果状态
	private boolean success;

	//支付模式 
	private PaymentType type;

	//订单编号
	private String orderCode;

	//支付金额
	private String fee;

	//支付结果，如果支付失败，会有此数据
	private String errorMsg;

	//第三方回调返回的原始数据
	private String originalData;

	public boolean isSuccess()
	{
		return success;
	}

	public void setSuccess(boolean success)
	{
		this.success = success;
	}

	public PaymentType getType()
	{
		return type;
	}

	public void setType(PaymentType type)
	{
		this.type = type;
	}

	public String getOrderCode()
	{
		return orderCode;
	}

	public void setOrderCode(String orderCode)
	{
		this.orderCode = orderCode;
	}

	public String getFee()
	{
		return fee;
	}

	public void setFee(String fee)
	{
		this.fee = fee;
	}

	public String getErrorMsg()
	{
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg)
	{
		this.errorMsg = errorMsg;
	}

	public String getOriginalData()
	{
		return originalData;
	}

	public void setOriginalData(String originalData)
	{
		this.originalData = originalData;
	}

	@Override
	public String toString()
	{
		return "PaymentResult [success=" + success + ", type=" + type + ", orderCode=" + orderCode + ", fee=" + fee + ", errorMsg="
				+ errorMsg + ", originalData=" + originalData + "]";
	}

}
