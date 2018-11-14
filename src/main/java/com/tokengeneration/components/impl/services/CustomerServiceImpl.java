package com.tokengeneration.components.impl.services;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tokengeneration.components.repositories.CustomerServiceDAO;
import com.tokengeneration.components.services.CustomerService;
import com.tokengeneration.components.utilities.CustomerUtility;
import com.tokengeneration.data.CustomerDataCache;
import com.tokengeneration.domains.dto.CustomerDTO;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerServiceDAO customerServiceDAO;

	@Autowired
	private CustomerUtility customerUtility;
	
	private CustomerDataCache customerDataCache = CustomerDataCache.getInstance();
	
	@Override
	public CustomerDTO getCustomerDetails(Long phoneNumber) {
		Supplier<CustomerDTO> customer;
		customer = () -> customerUtility.getCustomerByPhone (phoneNumber);
		if(Objects.nonNull(customer.get())) {
			return customer.get();
		}
		return null;
	}

	@Override
	@Transactional
	public CustomerDTO createNewCustomer(CustomerDTO newCustomer) {
		Optional<Long> id = null;
		id = customerServiceDAO.createNewCustomer(newCustomer);
		if(id.isPresent()) {
			newCustomer.setId(id.get());
			customerDataCache.put(newCustomer);
			return newCustomer;
		}
		return null;
	}
	
}