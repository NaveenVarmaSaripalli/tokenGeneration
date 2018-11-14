package com.tokengeneration.components.services;

import org.springframework.stereotype.Component;

import com.tokengeneration.domains.dto.TokenDTO;

@Component
public interface NotifierService {
	
	public void reArrangeTokens(Long branchId, Long bankServiceId);
}
