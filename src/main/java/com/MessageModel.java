package com;

import java.util.List;

public class MessageModel {
	public String msg;
    public List<Device> obj;
    public String gmac;
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public List<Device> getObj() {
		return obj;
	}
	public void setObj(List<Device> obj) {
		this.obj = obj;
	}
	public String getGmac() {
		return gmac;
	}
	public void setGmac(String gmac) {
		this.gmac = gmac;
	}
    
    
}

   
