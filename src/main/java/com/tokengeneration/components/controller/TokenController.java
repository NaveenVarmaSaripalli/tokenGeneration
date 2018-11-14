package com.tokengeneration.components.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tokengeneration.components.services.CustomValidator;
import com.tokengeneration.components.services.TokenService;
import com.tokengeneration.components.services.ValidatorService;
import com.tokengeneration.domains.dto.TokenDTO;
import com.tokengeneration.domains.requests.TokenCreationRequest;

@RestController
@RequestMapping("token")
public class TokenController {
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private ValidatorService validatorService;
	
	@PostMapping("/new")
	public ResponseEntity<TokenDTO>  createNewToken(@RequestBody TokenCreationRequest tokenCreationRequest) {
		if(Objects.isNull(tokenCreationRequest)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		CustomValidator<TokenCreationRequest> input = () -> tokenCreationRequest.isValid();
		if(!input.isValid()) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}
		else {
			TokenDTO tokenDTO = null;
			tokenDTO = tokenService.createNewToken(tokenCreationRequest);
			if(Objects.nonNull(tokenDTO)) {
				return new ResponseEntity<TokenDTO>(tokenDTO, HttpStatus.OK);
			}
			else {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	}
}
