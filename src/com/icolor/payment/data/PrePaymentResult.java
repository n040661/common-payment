package com.icolor.payment.data;

public class PrePaymentResult
{
	//预下单请求结果
	private boolean success;

	//预下单错误时，返回的错误信息
	private String errorMsg;

	//请求成功时，返回的数据
	private String data;

	public boolean isSuccess()
	{
		return success;
	}

	public void setSuccess(boolean success)
	{
		this.success = success;
	}

	public String getErrorMsg()
	{
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg)
	{
		this.errorMsg = errorMsg;
	}

	public String getData()
	{
		return data;
	}

	public void setData(String data)
	{
		this.data = data;
	}

	@Override
	public String toString()
	{
		return "PrePaymentResult [success=" + success + ", errorMsg=" + errorMsg + ", data=" + data + "]";
	}


}
