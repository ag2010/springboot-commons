package com.hr.paramenum;

public enum OrderTypeEnum {
	
	SELFHELP("自主擦鞋订单","1"),
	DOORTODOOR("上门擦鞋订单","2"),
	REPAIR("鞋包修护订单","3"),
	
	/**
	 * 优惠卡，优惠券
	 */
	CARD("优惠卡","1"),
	COUPON("优惠券","0"),
	
	PAY_WAIT_PAY("待支付","0"),
	PAY_SUCCESS("支付成功","1"),
	PAY_FALUE("支付失败","2"),
	PAY_CANCEL("取消支付","3"),
	
	SHOES_INIT("未开始擦鞋","0"),
	SHOES_ING("擦鞋进行中","1"),
	SHOES_SUCC("擦鞋成功","2"),
	SHOES_ERROR("擦鞋失败或故障","3"),
	
	/**
	 * 付款类型
	 */
	APLIPAY("支付宝","1"),
	WECHAT("微信","2");
	
	
	private String name;
	private String code;
	
	
	OrderTypeEnum(String name,String code) {
		this.name=name;
		this.code=code;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}
	 
	 
}
