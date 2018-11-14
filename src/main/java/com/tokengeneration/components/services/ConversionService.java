package com.tokengeneration.components.services;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.tokengeneration.domains.dto.CustomerDTO;
import com.tokengeneration.domains.requests.Customer;

@Component
public interface ConversionService {
	
	public Optional<CustomerDTO> convertDomainToDTO(Customer customer);
}
