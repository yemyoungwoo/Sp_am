package com.ymw.exam.demo.vo;

import lombok.Getter;

public class ResultData<DT> {
	@Getter
	// S-1, F-1, F-2 ...
	private String resultCode;
	@Getter
	private String msg;
	@Getter
	private String data1Name;
	@Getter
	private DT data1;
	
	public static <DT> ResultData<DT> from(String resultCode, String msg) {
		return from(resultCode, msg, null, null);
	}
	
	public static <DT> ResultData<DT> from(String resultCode, String msg, String data1Name, DT data1) {
		ResultData<DT> rd = new ResultData<>();
		rd.resultCode = resultCode;
		rd.msg = msg;
		rd.data1Name = data1Name;
		rd.data1 = data1;
		
		return rd;
	}
	
	public boolean isSuccess() {
		return this.resultCode.startsWith("S-");
	}
	
	public boolean isFail() {
		return isSuccess() == false;
	}

}