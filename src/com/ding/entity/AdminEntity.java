package com.ding.entity;

//用来存放 输入框中输入的管理员信息
public class AdminEntity {
	private String userName = null;
	private String userPassword = null;
	
	
	public AdminEntity() {
		
	}
	
	public AdminEntity(String userName, String userPassword) {
		super();
		this.userName = userName;
		this.userPassword = userPassword;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	
}
