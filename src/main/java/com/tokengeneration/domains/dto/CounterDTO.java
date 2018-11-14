package com.tokengeneration.domains.dto;

import java.io.Serializable;

public class CounterDTO implements Serializable {

	private static final long serialVersionUID = 6851736962477234392L;
	private long id;
	private long serviceId;
	private long branchId;
	private String name;
	private String status;

	public long getId() {
		return id;
	}

	public long getServiceId() {
		return serviceId;
	}

	public long getBranchId() {
		return branchId;
	}

	public String getName() {
		return name;
	}

	public String getStatus() {
		return status;
	}

	@Override
	public String toString() {
		return "Counter [id=" + id + ", serviceId=" + serviceId + ", branchId=" + branchId + ", name=" + name
				+ ", status=" + status + "]";
	}

	public CounterDTO(long id, long serviceId, long branchId, String name, String status) {
		super();
		this.id = id;
		this.serviceId = serviceId;
		this.branchId = branchId;
		this.name = name;
		this.status = status;
	}

}
