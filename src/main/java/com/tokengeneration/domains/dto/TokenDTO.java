package com.tokengeneration.domains.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class TokenDTO implements Serializable {
	
	private static final long serialVersionUID = 4279146407464498576L;
	private Long id;
	private CustomerDTO customer;
	private Long counterId;
	private Long bankServiceId;
	private Long branchId;
	private String tokenCode;
	private String status;
	private Timestamp created;
	private Timestamp updated;
	private int originalFootCount;
	private int presentFootCount;
	
	
	public TokenDTO(Long id, CustomerDTO customer, Long counterId, Long bankServiceId, Long branchId, String tokenCode,
			String status, Timestamp created, Timestamp updated, int originalFootCount, int presentFootCount) {
		super();
		this.id = id;
		this.customer = customer;
		this.counterId = counterId;
		this.bankServiceId = bankServiceId;
		this.branchId = branchId;
		this.tokenCode = tokenCode;
		this.status = status;
		this.created = created;
		this.updated = updated;
		this.originalFootCount = originalFootCount;
		this.presentFootCount = presentFootCount;
	}


	public Long getId() {
		return id;
	}

	public CustomerDTO getCustomer() {
		return customer;
	}


	public Long getCounterId() {
		return counterId;
	}


	public Long getBankServiceId() {
		return bankServiceId;
	}


	public Long getBranchId() {
		return branchId;
	}


	public String getTokenCode() {
		return tokenCode;
	}


	public String getStatus() {
		return status;
	}


	public Timestamp getCreated() {
		return created;
	}


	public Timestamp getUpdated() {
		return updated;
	}


	public int getOriginalFootCount() {
		return originalFootCount;
	}


	public int getPresentFootCount() {
		return presentFootCount;
	}


	public boolean isPreviliged() {
		return tokenCode.startsWith("P");
	}


	public void setPresentFootCount(int presentFootCount) {
		this.presentFootCount = presentFootCount;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public void setCustomer(CustomerDTO customer) {
		this.customer = customer;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public void setCounterId(Long counterId) {
		this.counterId = counterId;
	}

	
}
