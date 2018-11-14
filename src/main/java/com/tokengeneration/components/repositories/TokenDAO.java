package com.tokengeneration.components.repositories;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.tokengeneration.domains.dto.TokenDTO;

@Component
public interface TokenDAO {

	public Optional<Long> createNewToken(TokenDTO token);

	public Optional<TokenDTO> getTokenByStatusAndCounter(Long counterId, String tokenStatus);

	public Optional<TokenDTO> fetchNewToken(Long branchId, Long serviceId, String tokenStatus);

	public Optional<Integer> setTokenStatusAndCounter(Long tokenId, Long counterId, String tokenStatus);

	public Optional<Integer> changeTokenStatus(String tokenId, String requestedStatus);

	public Optional<Integer> changeTokenCounter(String tokenId, String nextCounterId);

	public Optional<TokenDTO> getTokenDetails (Long id);

}
