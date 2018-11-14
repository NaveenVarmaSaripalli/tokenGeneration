package com.tokengeneration.domains.requests;

import java.util.Objects;

public class Customer {

	private Long id;
	private String name;
	private String phoneNumber;
	private String homeBranchId;
	
	public String getName() {
		return name;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public String getHomeBranchId() {
		return homeBranchId;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setHomeBranchId(String homeBranchId) {
		this.homeBranchId = homeBranchId;
	}

	public Long getId() {
		return id;
	}
	
	public Customer(Long id, String name, String phoneNumber, boolean isValuedCustomer, String homeBranchId) {
		super();
		this.id = id;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.homeBranchId = homeBranchId;
	}
	
	public boolean isValidCustomer() {
		if(Objects.nonNull(this) && Objects.nonNull(this.name) && Objects.nonNull(this.phoneNumber) && Objects.nonNull(this.homeBranchId)) {
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "Customer [name=" + name + ", phoneNumber=" + phoneNumber + ", homeBranchId=" + homeBranchId + "]";
	}
	
}
