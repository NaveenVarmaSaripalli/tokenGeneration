package com.tokengeneration.components.utilities;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tokengeneration.components.repositories.CustomerServiceDAO;
import com.tokengeneration.components.services.ValidatorService;
import com.tokengeneration.data.CustomerDataCache;
import com.tokengeneration.domains.dto.CustomerDTO;

@Component
public final class CustomerUtility {

	@Autowired
	private CustomerServiceDAO customerServiceDAO;

	@Autowired
	private ValidatorService validatorService;

	private CustomerDataCache customerDataCache = CustomerDataCache.getInstance();

	public CustomerDTO getCustomerDetailsById(Long id) {
		if (Objects.nonNull(id) && validatorService.isValidNumber(id.toString())) {
			Optional<CustomerDTO> customer = customerDataCache.get(id);
			if (customer.isPresent()) {
				return customer.get();
			} else {
				customer = customerServiceDAO.getCustomerDetailsById(id);
				if (customer.isPresent()) {
					customerDataCache.put(customer.get());
					return customer.get();
				}
			}
		}
		return null;
	}

	public CustomerDTO getCustomerByPhone(Long phoneNumber) {
		if (Objects.nonNull(phoneNumber) && validatorService.isValidNumber(phoneNumber.toString())) {
			Optional<CustomerDTO> customer = customerDataCache.get(phoneNumber.toString());
			if (customer.isPresent()) {
				return customer.get();
			} else {
				customer = customerServiceDAO.getCustomerDetails(phoneNumber);
				if (customer.isPresent()) {
					customerDataCache.put(customer.get());
					return customer.get();
				}
			}
		}
		return null;
	}
}
