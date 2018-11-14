package com.tokengeneration.domains.requests;

import java.util.Objects;

public class CounterModificationRequest {

	private Long counterId;
	private String presentStatus;
	private String requestedStatus;
	
	public Long getCounterId() {
		return counterId;
	}

	public void setCounterId(Long counterId) {
		this.counterId = counterId;
	}
	
	public String getPresentStatus() {
		return presentStatus;
	}
	
	public void setPresentStatus(String presentStatus) {
		this.presentStatus = presentStatus;
	}
	
	public String getRequestedStatus() {
		return requestedStatus;
	}
	
	public void setRequestedStatus(String requestedStatus) {
		this.requestedStatus = requestedStatus;
	}

	@Override
	public String toString() {
		return "CounterModificationRequest [counterId=" + counterId + ", presentStatus=" + presentStatus
				+ ", requestedStatus=" + requestedStatus + "]";
	}

	public boolean isValid() {
		return Objects.nonNull(this) && Objects.nonNull(presentStatus) && Objects.nonNull(requestedStatus) && Objects.nonNull(counterId);
	}
	
}
