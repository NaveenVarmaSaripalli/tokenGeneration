package com.tokengeneration.components.services;

import org.springframework.stereotype.Component;

@Component
public interface NotifierService {
	
	public void reArrangeTokens(Long branchId, Long bankServiceId);
}
