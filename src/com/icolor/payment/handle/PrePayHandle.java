package com.icolor.payment.handle;

import com.icolor.payment.data.PrePaymentResult;

public interface PrePayHandle
{
	PrePaymentResult prePay(String orderCode, String fee);
}
