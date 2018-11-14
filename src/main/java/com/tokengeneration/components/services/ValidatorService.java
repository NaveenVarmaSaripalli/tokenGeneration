package com.tokengeneration.components.services;

import org.springframework.stereotype.Component;

@Component
public interface ValidatorService {

	public boolean isValidNumber(String input);
	
}
