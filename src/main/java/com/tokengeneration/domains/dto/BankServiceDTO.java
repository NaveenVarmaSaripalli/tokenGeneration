package com.tokengeneration.domains.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BankServiceDTO implements Serializable{

	private static final long serialVersionUID = 7556699262519339333L;
	private long id;
	private String name;
	private Long branchId;
	private List<Long> counters;
	private int previligedFootCount;
	private int nonPreviligedFootCount;
	private ArrayList<TokenDTO> tokenQueue;

	public Integer incrementPreviligedFootCount() {
		return ++this.previligedFootCount;
	}

	public Integer incrementNonPreviligedFootCount() {
		return ++this.nonPreviligedFootCount;
	}

	public String getName() {
		return name;
	}

	public int getPreviligedFootCount() {
		return previligedFootCount;
	}

	public int getNonPreviligedFootCount() {
		return nonPreviligedFootCount;
	}

	public long getId() {
		return this.id;
	}

	public synchronized ArrayList<TokenDTO> reArrangeTokens() {
		int queueSize = this.tokenQueue.size();
		TokenDTO tempToken;
		if (queueSize > 4) {
			TokenDTO newToken = this.tokenQueue.get(queueSize - 1);
			int count = 0;
			if (newToken.isPreviliged()) {
				for (int i = 1; i <= 3; i++) {
					int index = queueSize - (i + 1);
					tempToken = this.tokenQueue.get(index);
					int footChange = tempToken.getPresentFootCount() - tempToken.getOriginalFootCount();
					if (!tempToken.isPreviliged() && footChange < 3) {
						tempToken.setPresentFootCount(tempToken.getPresentFootCount() + 1);
						this.tokenQueue.set(index, newToken);
						this.tokenQueue.set(index + 1, tempToken);
						count++;
					} else {
						break;
					}
				}
				newToken.setPresentFootCount(queueSize - count);
			}
		}
		return this.tokenQueue;
	}

	@Override
	public String toString() {
		return "BankService [id=" + id + ", name=" + name + ", counters=" + counters + ", previligedFootCount="
				+ previligedFootCount + ", nonPreviligedFootCount=" + nonPreviligedFootCount + ", tokenQueue="
				+ tokenQueue + "]";
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setCounters(List<Long> counters) {
		this.counters = counters;
	}

	public void setPreviligedFootCount(int previligedFootCount) {
		this.previligedFootCount = previligedFootCount;
	}

	public void setNonPreviligedFootCount(int nonPreviligedFootCount) {
		this.nonPreviligedFootCount = nonPreviligedFootCount;
	}

	public void setTokenQueue(ArrayList<TokenDTO> tokenQueue) {
		this.tokenQueue = tokenQueue;
	}

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}
}
