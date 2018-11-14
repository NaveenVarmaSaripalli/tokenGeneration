package com.tokengeneration.data;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

import com.tokengeneration.domains.dto.CustomerDTO;

public final class CustomerDataCache {

	private Map<Long, CustomerDTO> customers;

	private CustomerDataCache() {
		customers = new ConcurrentHashMap<Long, CustomerDTO>();
	}

	public static CustomerDataCache getInstance() {
		return CustomerCache.customerDataCache;
	}

	private Map<Long, CustomerDTO> getCustomers() {
		return customers;
	}

	public synchronized void put(CustomerDTO customerDTO) {
		Optional<CustomerDTO> customer = this.get(customerDTO.getId());
		if (!customer.isPresent()) {
			this.getCustomers().put(customerDTO.getId(), customerDTO);
		}
	}

	public Optional<CustomerDTO> get(String phoneNumber) {
		Optional<CustomerDTO> customer = this.getCustomers().values().stream()
				.filter(s -> s.getPhoneNumber().equals(phoneNumber)).findFirst();
		return customer;
	}

	public Optional<CustomerDTO> get(Long id) {
		Supplier<CustomerDTO> customer = () -> this.getCustomers().get(id);
		return Optional.ofNullable(customer.get());
	}

	private static class CustomerCache {
		public static CustomerDataCache customerDataCache = new CustomerDataCache();
	}

	public CustomerDataCache clone() {
		return getInstance();
	}

}
