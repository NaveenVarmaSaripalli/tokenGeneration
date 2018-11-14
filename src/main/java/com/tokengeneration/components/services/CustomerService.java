package com.tokengeneration.components.services;

import org.springframework.stereotype.Component;

import com.tokengeneration.domains.dto.CustomerDTO;

@Component
public interface CustomerService {

	public CustomerDTO getCustomerDetails(Long phoneNumber);
	
	public CustomerDTO createNewCustomer(CustomerDTO newCustomer);

}
