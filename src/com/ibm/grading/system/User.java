package com.ibm.grading.system;

public class User extends Account {
	private String name;
	private String address;
	private String gender;
	private String userId;
	private String accountId;

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountID) {
		this.accountId = accountID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String studentId) {
		this.userId = studentId;
	}

}
