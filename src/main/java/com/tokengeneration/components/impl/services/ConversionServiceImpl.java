package com.tokengeneration.components.impl.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tokengeneration.components.services.ConversionService;
import com.tokengeneration.domains.dto.CustomerDTO;
import com.tokengeneration.domains.requests.Customer;

@Service
public class ConversionServiceImpl implements ConversionService {

	@Override
	public Optional<CustomerDTO> convertDomainToDTO(Customer customer) {
		CustomerDTO customerDTO = null;
		if (customer.isValidCustomer()) {
			customerDTO = new CustomerDTO(customer.getId(), customer.getName(), customer.getPhoneNumber(), false,
					customer.getHomeBranchId());
		}
		return Optional.ofNullable(customerDTO);
	}

}
