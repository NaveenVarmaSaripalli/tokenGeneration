package com.tokengeneration.components.utilities;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tokengeneration.components.repositories.CounterDAO;
import com.tokengeneration.components.services.ValidatorService;
import com.tokengeneration.data.CounterCache;
import com.tokengeneration.domains.dto.CounterDTO;

@Component
public class CounterUtility {

	@Autowired
	private CounterDAO counterDAO;

	@Autowired
	private ValidatorService validatorService;
	
	private CounterCache counterCache = CounterCache.getInstance();

	@Transactional
	public CounterDTO getCounterDTO(Long counterId) {
		if (Objects.nonNull(counterId) && validatorService.isValidNumber(counterId.toString())) {
			Optional<CounterDTO> counterDTO = counterCache.get(counterId);
			if(counterDTO.isPresent()) {
				return counterDTO.get();
			}
			else {
				counterDTO = counterDAO.getCounterDTO(counterId);
				if (counterDTO.isPresent()) {
					counterCache.put(counterDTO.get());
					return counterDTO.get();
				}
			}
		}
		return null;
	}
}
