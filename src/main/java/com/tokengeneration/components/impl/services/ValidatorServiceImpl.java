package com.tokengeneration.components.impl.services;

import org.springframework.stereotype.Service;

import com.tokengeneration.components.services.ValidatorService;

@Service
public class ValidatorServiceImpl implements ValidatorService {
	
	public boolean isValidNumber(String input) {
		return input.matches("[0-9]+");
	}

}

