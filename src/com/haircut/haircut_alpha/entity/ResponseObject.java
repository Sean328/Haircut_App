package com.haircut.haircut_alpha.entity;

public class ResponseObject<T> {
	
	private int state;
	private T datas;
	private String msg;
	
	public ResponseObject(){
		super();
	}
	
	public ResponseObject(int state,T datas){
		this.state = state;
		this.datas = datas;
	}
	
	public int getState(){
		return state;
	}
	
	public void setState(int state){
		this.state = state;
	}
	
	public T getDatas(){
		return datas;
	}
	
	public void setDatas(T datas){
		this.datas = datas;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	

}
