package com.tokengeneration.domains.dto;

import java.io.Serializable;

public class CustomerDTO implements Serializable {

	private static final long serialVersionUID = 7355737148808201640L;
	private Long id;
	private String name;
	private String phoneNumber;
	private boolean isValuedCustomer;
	private String homeBranchId;

	public boolean isValuedCustomer() {
		return isValuedCustomer;
	}

	public String getName() {
		return name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getHomeBranchId() {
		return homeBranchId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CustomerDTO(Long id, String name, String phoneNumber, boolean isValuedCustomer, String homeBranchId) {
		super();
		this.id = id;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.isValuedCustomer = isValuedCustomer;
		this.homeBranchId = homeBranchId;
	}

	@Override
	public String toString() {
		return "Customer [name=" + name + ", phoneNumber=" + phoneNumber + ", isValuedCustomer=" + isValuedCustomer
				+ ", homeBranchId=" + homeBranchId + "]";
	}

}
