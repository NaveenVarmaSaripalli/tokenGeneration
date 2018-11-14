package com.tokengeneration.components.services;

import org.springframework.stereotype.Component;

import com.tokengeneration.domains.dto.TokenDTO;
import com.tokengeneration.domains.requests.CounterModificationRequest;

@Component
public interface CounterService {

	public TokenDTO getTokenToServe(Long counterId);

	public String modifyCounterStatus(CounterModificationRequest counterModificationRequest);

}
