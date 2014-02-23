package com.zhuhongqing.test;

public class TheServerIsBusy extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TheServerIsBusy() {
		super("TheServerIsBusy");
	}
}
