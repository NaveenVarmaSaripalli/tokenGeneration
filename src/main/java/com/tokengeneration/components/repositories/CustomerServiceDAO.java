package com.tokengeneration.components.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.tokengeneration.domains.dto.CustomerDTO;

@Component
public interface CustomerServiceDAO {

	public Optional<CustomerDTO>  getCustomerDetails(Long phoneNumber);

	public Optional<Long> createNewCustomer(CustomerDTO newCustomer);

	public Optional<CustomerDTO> getCustomerDetailsById(Long id);

	public Optional<List<CustomerDTO>> getCustomerDetails();
	
}
