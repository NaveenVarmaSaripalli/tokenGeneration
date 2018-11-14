package com.tokengeneration.domains.requests;

import java.util.Objects;

public class TokenCounterChangeRequest {

	private String tokenId;
	private String presentCounterId;
	private String nextCounterId;

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public String getPresentCounterId() {
		return presentCounterId;
	}

	public void setPresentCounterId(String presentCounterId) {
		this.presentCounterId = presentCounterId;
	}

	public String getNextCounterId() {
		return nextCounterId;
	}

	public void setNextCounterId(String nextCounterId) {
		this.nextCounterId = nextCounterId;
	}
	
	public boolean isValid() {
		return Objects.nonNull(this) && Objects.nonNull(this.tokenId) && Objects.nonNull(this.presentCounterId) && Objects.nonNull(this.nextCounterId);
	}

}
