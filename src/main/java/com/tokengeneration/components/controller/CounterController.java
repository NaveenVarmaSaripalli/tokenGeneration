package com.tokengeneration.components.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tokengeneration.components.services.ConversionService;
import com.tokengeneration.components.services.CounterService;
import com.tokengeneration.components.services.ValidatorService;
import com.tokengeneration.domains.dto.TokenDTO;

@RestController
@RequestMapping("counter")
public class CounterController {

	@Autowired
	CounterService counterService;
	
	@Autowired
	ConversionService conversionService;
	
	@Autowired
	ValidatorService validatorService;

	@GetMapping("/{id}/token")
	public ResponseEntity<TokenDTO> getTokenToServe(@PathVariable(name = "id") Long counterId) {
		if(Objects.isNull(counterId)) {
			return new ResponseEntity<TokenDTO>(HttpStatus.NOT_ACCEPTABLE);
		}
		if(validatorService.isValidNumber(counterId.toString())) {
			TokenDTO tokenDTO = counterService.getTokenToServe(counterId);
			if(Objects.isNull(tokenDTO)) {
				return new ResponseEntity<>(tokenDTO, HttpStatus.OK);//TODO: HTTP Status Code need to be changed.
			}
			return new ResponseEntity<TokenDTO>(tokenDTO, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
