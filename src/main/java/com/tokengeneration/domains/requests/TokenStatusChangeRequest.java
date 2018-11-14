package com.tokengeneration.domains.requests;

import java.util.Objects;

public class TokenStatusChangeRequest {

	private String tokenId;
	private String presentStatus;
	private String requestedStatus;

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
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

	public boolean isValid() {
		if(Objects.nonNull(this) && Objects.nonNull(tokenId) && Objects.nonNull(presentStatus) && Objects.nonNull(requestedStatus)) {
			return true;
		}
		return false;
	}
}
