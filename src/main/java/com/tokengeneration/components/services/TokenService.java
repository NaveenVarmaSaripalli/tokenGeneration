package com.tokengeneration.components.services;

import org.springframework.stereotype.Component;

import com.tokengeneration.domains.dto.TokenDTO;
import com.tokengeneration.domains.requests.TokenCounterChangeRequest;
import com.tokengeneration.domains.requests.TokenCreationRequest;
import com.tokengeneration.domains.requests.TokenStatusChangeRequest;

@Component
public interface TokenService {

	public TokenDTO createNewToken(TokenCreationRequest tokenCreationRequest);

	public Integer changeTokenStatus(TokenStatusChangeRequest tokenStatusChangeRequest);

	public Integer changeTokenCounter(TokenCounterChangeRequest tokenCounterChangeRequest);

}
