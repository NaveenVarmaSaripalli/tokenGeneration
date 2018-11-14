package com.tokengeneration.components.impl.services;

import java.util.Objects;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tokengeneration.components.services.CounterService;
import com.tokengeneration.components.services.CustomValidator;
import com.tokengeneration.components.services.ValidatorService;
import com.tokengeneration.components.utilities.CounterUtility;
import com.tokengeneration.components.utilities.TokenUtility;
import com.tokengeneration.domains.dto.CounterDTO;
import com.tokengeneration.domains.dto.TokenDTO;
import com.tokengeneration.domains.requests.CounterModificationRequest;
import com.tokengeneration.enums.TokenStatus;

@Service
public class CounterServiceImpl implements CounterService {

	@Autowired
	private ValidatorService validatorService;

	@Autowired
	private TokenUtility tokenUtility;
	
	@Autowired
	private CounterUtility counterUtility;
	
	@Override
	public TokenDTO getTokenToServe(Long counterId) {
		TokenDTO tokenDTO = null;
		if (Objects.nonNull(counterId) && validatorService.isValidNumber(counterId.toString())) {
			Supplier<CounterDTO> counterDTO = () -> counterUtility.getCounterDTO(counterId);
			if(Objects.nonNull(counterDTO.get())) {
				tokenDTO = tokenUtility.assignTokenToCounter(counterDTO.get());
			}
			if (Objects.nonNull(tokenDTO)) {
				return tokenDTO;
			}
		}
		return tokenDTO;
	}

	@Override
	public String modifyCounterStatus(CounterModificationRequest counterModificationRequest) {
		CustomValidator<CounterModificationRequest> input = () -> counterModificationRequest.isValid();
		TokenDTO tokenDTO = null;
		CounterDTO counterDTO = null;
		if(input.isValid()) {
			counterDTO = counterUtility.getCounterDTO(counterModificationRequest.getCounterId());
			tokenDTO = tokenUtility.getTokenByStatusAndCounter(counterModificationRequest.getCounterId(), TokenStatus.PROCESSING.name());
		}
		return null;
	}
	
}