package com.tokengeneration.components.controller;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tokengeneration.components.services.ConversionService;
import com.tokengeneration.components.services.CustomValidator;
import com.tokengeneration.components.services.CustomerService;
import com.tokengeneration.components.services.ValidatorService;
import com.tokengeneration.domains.dto.CustomerDTO;
import com.tokengeneration.domains.requests.Customer;

@RestController
@RequestMapping("customer")
public class CustomerController {

	@Autowired
	CustomerService customerService;
	
	@Autowired
	ConversionService conversionService;
	
	@Autowired
	ValidatorService validatorService;

	@GetMapping("/{phoneNumber}")
	public ResponseEntity<CustomerDTO> getCustomerDetails(@PathVariable(name = "phoneNumber") String phoneNumber) {
		if(Objects.isNull(phoneNumber)) {
			return new ResponseEntity<CustomerDTO>(HttpStatus.NOT_ACCEPTABLE);
		}
		if(validatorService.isValidNumber(phoneNumber)) {
			CustomerDTO customer = customerService.getCustomerDetails(Long.valueOf(phoneNumber));
			if(Objects.isNull(customer)) {
				return new ResponseEntity<>(customer, HttpStatus.OK);//TODO: HTTP Status Code need to be changed.
			}
			return new ResponseEntity<CustomerDTO>(customer, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/new")
	public ResponseEntity<CustomerDTO>  createNewCustomer(@RequestBody Customer newCustomer) {
		if(Objects.isNull(newCustomer)) {
			return new ResponseEntity<CustomerDTO>(HttpStatus.NOT_ACCEPTABLE);
		}
		CustomValidator<Customer> input = () -> newCustomer.isValidCustomer();
		if(input.isValid() && validatorService.isValidNumber(newCustomer.getPhoneNumber().toString())) {
			Optional<CustomerDTO> customerDTO = conversionService.convertDomainToDTO(newCustomer);
			if(customerDTO.isPresent()) {
				CustomerDTO customer = customerService.createNewCustomer(customerDTO.get());
				if(Objects.nonNull(customerDTO)) {
					return new ResponseEntity<CustomerDTO>(customer, HttpStatus.CREATED);
				}
				return new ResponseEntity<CustomerDTO>(HttpStatus.EXPECTATION_FAILED); //TODO: Check the HTTP Status code here
			}
			else {
				return new ResponseEntity<CustomerDTO>(HttpStatus.BAD_REQUEST);
			}
		}
		else {
			return new ResponseEntity<CustomerDTO>(HttpStatus.BAD_REQUEST);
		}
	}
}
