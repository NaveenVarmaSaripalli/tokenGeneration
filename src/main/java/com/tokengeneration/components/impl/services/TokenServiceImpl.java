package com.tokengeneration.components.impl.services;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tokengeneration.components.repositories.TokenDAO;
import com.tokengeneration.components.services.CustomValidator;
import com.tokengeneration.components.services.NotifierService;
import com.tokengeneration.components.services.TokenService;
import com.tokengeneration.components.services.ValidatorService;
import com.tokengeneration.components.utilities.BankServiceUtility;
import com.tokengeneration.components.utilities.BranchUtility;
import com.tokengeneration.components.utilities.CustomerUtility;
import com.tokengeneration.components.utilities.TokenUtility;
import com.tokengeneration.data.TokenCache;
import com.tokengeneration.domains.dto.BankServiceDTO;
import com.tokengeneration.domains.dto.BranchDTO;
import com.tokengeneration.domains.dto.CustomerDTO;
import com.tokengeneration.domains.dto.TokenDTO;
import com.tokengeneration.domains.requests.TokenCounterChangeRequest;
import com.tokengeneration.domains.requests.TokenCreationRequest;
import com.tokengeneration.domains.requests.TokenStatusChangeRequest;

@Service
public class TokenServiceImpl implements TokenService {

	@Autowired
	private CustomerUtility customerUtility;

	@Autowired
	private TokenUtility tokenUtility;

	@Autowired
	private BranchUtility branchUtility;

	@Autowired
	private BankServiceUtility bankServiceUtility;

	@Autowired
	private NotifierService notifierService;

	@Autowired
	private ValidatorService validatorService;

	@Autowired
	TokenDAO tokenDAO;

	@Override
	public TokenDTO createNewToken(TokenCreationRequest tokenCreationRequest) {
		CustomerDTO customerDTO = null;
		BankServiceDTO bankService = null;
		BranchDTO branch = null;
		TokenDTO token = null;
		CustomValidator<TokenCreationRequest> input = () -> tokenCreationRequest.isValid();
		if (input.isValid()) {
			try {
				if (validatorService.isValidNumber(tokenCreationRequest.getCustomerId().toString())) {
					customerDTO = customerUtility.getCustomerDetailsById(tokenCreationRequest.getCustomerId());
				}

				if (validatorService.isValidNumber(tokenCreationRequest.getServiceId().toString())
						&& validatorService.isValidNumber(tokenCreationRequest.getBranchId().toString())) {
					bankService = bankServiceUtility.getBankServiceDTO(tokenCreationRequest.getServiceId(),
							tokenCreationRequest.getBranchId());
					branch = branchUtility.getBranchDetails(tokenCreationRequest.getBranchId());
				}

				if (Objects.nonNull(customerDTO) && Objects.nonNull(bankService) && Objects.nonNull(branch)) {
					token = tokenUtility.createNewToken(customerDTO, bankService, branch);
				}

				if (Objects.nonNull(token) && Objects.nonNull(token.getId())) {
					TokenCache tokenCache = TokenCache.getInstance();
					/*
					 * synchronized (tokenCache) { new Thread (() -> {
					 * notifierService.reArrangeTokens(); }).start(); }
					 */
					return token;
				}
			} catch (NullPointerException nullPointerException) {
				return null;
			}
		}
		return null;
	}

	@Override
	public Integer changeTokenStatus(TokenStatusChangeRequest tokenStatusChangeRequest) {
		Optional<Integer> rowsAffected = null;
		CustomValidator<TokenStatusChangeRequest> input = () -> tokenStatusChangeRequest.isValid();
		try {
			if (input.isValid() && Objects.nonNull(tokenStatusChangeRequest)
					&& validatorService.isValidNumber(tokenStatusChangeRequest.getTokenId())) {
				rowsAffected = tokenDAO.changeTokenStatus(tokenStatusChangeRequest.getTokenId(),
						tokenStatusChangeRequest.getRequestedStatus());
				if (rowsAffected.isPresent()) {
					return rowsAffected.get();
				}
			}
		} catch (NullPointerException nullPointerException) {
			return null;
		}
		return null;
	}

	@Override
	public Integer changeTokenCounter(TokenCounterChangeRequest tokenCounterChangeRequest) {
		Optional<Integer> rowsAffected = null;
		CustomValidator<TokenCounterChangeRequest> input = () -> tokenCounterChangeRequest.isValid();
		try {
			if (input.isValid() && Objects.nonNull(tokenCounterChangeRequest)
					&& validatorService.isValidNumber(tokenCounterChangeRequest.getTokenId())) {
				rowsAffected = tokenDAO.changeTokenCounter(tokenCounterChangeRequest.getTokenId(),
						tokenCounterChangeRequest.getNextCounterId());
				if (rowsAffected.isPresent()) {
					return rowsAffected.get();
				}
			}
		} catch (NullPointerException nullPointerException) {
			return null;
		}
		return null;
	}

	private TokenDTO getTokenDTO(Long id) {
		Optional<TokenDTO> tokenDTO = null;
		if (Objects.nonNull(id) && validatorService.isValidNumber(id.toString())) {
			tokenDTO = tokenDAO.getTokenDetails(id);
			if (tokenDTO.isPresent()) {
				return tokenDTO.get();
			}
		}
		return null;
	}

}
