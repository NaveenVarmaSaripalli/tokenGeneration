package com.tokengeneration.components.repositories;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.tokengeneration.domains.dto.CounterDTO;

@Component
public interface CounterDAO {

	public Optional<CounterDTO> getCounterDTO(Long counterId) ;

}
