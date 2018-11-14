package com.tokengeneration.domains.requests;

import java.util.Objects;

/**
 * 
 * TokenCreationRequest is a request to generate a token when a new customer comes in and opts for a service.
 * 
 * 
 * @author Naveen Saripalli
 *
 */
public class TokenCreationRequest {

	private Long customerId;
	private Long serviceId;
	private Long branchId;

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}
	
	public boolean isValid() {
		if(Objects.nonNull(this) && Objects.nonNull(branchId) && Objects.nonNull(customerId) && Objects.nonNull(serviceId)) {
			return true;
		}
		return false;
	}

}
