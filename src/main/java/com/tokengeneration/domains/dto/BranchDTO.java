package com.tokengeneration.domains.dto;

import java.io.Serializable;
import java.util.List;

public class BranchDTO implements Serializable{

	private static final long serialVersionUID = 6908591777812495447L;
	private long id;
	private String branchCode;
	private String location;
	private List<BankServiceDTO> servicesOffered;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public List<BankServiceDTO> getServicesOffered() {
		return servicesOffered;
	}

	public void setServicesOffered(List<BankServiceDTO> servicesOffered) {
		this.servicesOffered = servicesOffered;
	}

	@Override
	public String toString() {
		return "Branch [id=" + id + ", branchCode=" + branchCode + ", location=" + location + ", servicesOffered="
				+ servicesOffered + "]";
	}

}
