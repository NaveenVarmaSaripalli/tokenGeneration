package com.tokengeneration.components.utilities;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tokengeneration.components.repositories.TokenDAO;
import com.tokengeneration.components.services.ValidatorService;
import com.tokengeneration.domains.dto.BankServiceDTO;
import com.tokengeneration.domains.dto.BranchDTO;
import com.tokengeneration.domains.dto.CounterDTO;
import com.tokengeneration.domains.dto.CustomerDTO;
import com.tokengeneration.domains.dto.TokenDTO;
import com.tokengeneration.enums.CounterStatus;
import com.tokengeneration.enums.TokenStatus;

@Component
public class TokenUtility {

	@Autowired
	private ValidatorService validatorService;
	
	@Autowired
	private CustomerUtility customerUtility;
	
	@Autowired
	private BankServiceUtility bankServiceUtility;
	
	@Autowired
	TokenDAO tokenDAO;
	
	@Transactional
	public TokenDTO createNewToken(CustomerDTO customer, BankServiceDTO bankServiceDTO, BranchDTO branch) {
		TokenDTO token = null;
		Optional<Long> id = null;
		if (Objects.nonNull(customer) && Objects.nonNull(bankServiceDTO) && Objects.nonNull(branch)) {
			try {
				String tokenCode = generateTokenName(bankServiceDTO, branch, customer.isValuedCustomer());
				int footCount = bankServiceDTO.getPreviligedFootCount() + bankServiceDTO.getNonPreviligedFootCount();
				token = new TokenDTO(null, customer, null, bankServiceDTO.getId(), branch.getId(), tokenCode,
						TokenStatus.CREATED.name(), new Timestamp(System.currentTimeMillis()),
						new Timestamp(System.currentTimeMillis()), footCount, footCount);
				id = tokenDAO.createNewToken(token);
				if (id.isPresent()) {
					token.setId(id.get());
				}
			} catch (NullPointerException nullPointerException) {
				return null;
			}
		}
		return token;
	}

	private synchronized String generateTokenName(BankServiceDTO bankServiceDTO, BranchDTO branch,
			boolean isValuedCustomer) {
		Supplier<String> tokenName = null;
		try {
			if (isValuedCustomer) {
				tokenName = () -> "P - " + branch.getBranchCode() + " - " + bankServiceDTO.getName() + " - "
						+ (bankServiceUtility.incrementPreviligedFootCount(bankServiceDTO.getId(), branch.getId())
								? bankServiceDTO.incrementPreviligedFootCount()
								: null);
			} else {
				tokenName = () -> "N - " + branch.getBranchCode() + " - " + bankServiceDTO.getName() + " - "
						+ (bankServiceUtility.incrementNonPreviligedFootCount(bankServiceDTO.getId(), branch.getId())
								? bankServiceDTO.incrementNonPreviligedFootCount()
								: null);
			}
		} catch (NullPointerException nullPointerException) {
			return null;
		}
		return tokenName.get();
	}
	
	public TokenDTO assignTokenToCounter(CounterDTO counterDTO) {
		Supplier<TokenDTO> tokenDTO = null;
		try {
			if (Objects.nonNull(counterDTO) && Objects.nonNull(counterDTO.getStatus())
					&& CounterStatus.ACTIVE.name().equalsIgnoreCase(counterDTO.getStatus())) {
				tokenDTO = () -> this.getTokenByStatusAndCounter(counterDTO.getId(), TokenStatus.PROCESSING.name());
				if (Objects.isNull(tokenDTO.get())) {
					tokenDTO = () -> this.getTokenByStatusAndCounter(counterDTO.getId(), TokenStatus.HELD_BY_COUNTER.name());
					if (Objects.isNull(tokenDTO.get())) {
						tokenDTO = () -> this.getTokenByStatusAndCounter(counterDTO.getId(), TokenStatus.WAITING.name());
						if (Objects.isNull(tokenDTO.get())) {
							tokenDTO = () -> this.fetchNewTokenForCounter(counterDTO.getBranchId(), counterDTO.getServiceId(),
									TokenStatus.CREATED.name(), counterDTO.getId());
						}
					}
				}
			}
		} catch (NullPointerException nullPointerException) {
			return null;
		}
		return tokenDTO.get();
	}

	private TokenDTO fetchNewTokenForCounter (Long branchId, Long serviceId, String tokenStatus, Long counterId) {
		if (Objects.nonNull(branchId) && validatorService.isValidNumber(branchId.toString())
				&& Objects.nonNull(serviceId) && validatorService.isValidNumber(serviceId.toString())
				&& Objects.nonNull(tokenStatus) && Objects.nonNull(counterId)
				&& validatorService.isValidNumber(counterId.toString())) {
			try {
				Supplier<TokenDTO> tokenDTO = null;
				tokenDTO = () -> this.assignNewTokenToCounter(branchId, serviceId, tokenStatus, counterId);
				return tokenDTO.get();
			} catch(NullPointerException nullPointerException) {
				return null;
			}
		}
		return null;
	}
	
	@Transactional
	public TokenDTO assignNewTokenToCounter(Long branchId, Long serviceId, String tokenStatus, Long counterId) {
		if (Objects.nonNull(branchId) && validatorService.isValidNumber(branchId.toString())
				&& Objects.nonNull(serviceId) && validatorService.isValidNumber(serviceId.toString())
				&& Objects.nonNull(tokenStatus) && Objects.nonNull(counterId)
				&& validatorService.isValidNumber(counterId.toString())) {
			Optional<TokenDTO> tokenDTO = null;
			try {
				tokenDTO = tokenDAO.fetchNewToken(branchId, serviceId, tokenStatus);
				if (Objects.nonNull(tokenDTO) && tokenDTO.isPresent()) {
					TokenDTO token = tokenDTO.get();
					if (Objects.nonNull(token)) {
						token = this.setCustomerDetailsForToken(token);
						Optional<Integer> numberOfRowsAffected = tokenDAO.setTokenStatusAndCounter(token.getId(),
								counterId, TokenStatus.PROCESSING.name());
						if (numberOfRowsAffected.isPresent() && numberOfRowsAffected.get() == 1) {
							token.setStatus(TokenStatus.PROCESSING.name());
							token.setCounterId(counterId);
						}
					}
					return token;
				}
			} catch (NullPointerException nullPointerException) {
				return null;
			}
		}
		return null;
	}
	
	public TokenDTO getTokenByStatusAndCounter(Long counterId, String tokenStatus) {
		if (Objects.nonNull(counterId) && validatorService.isValidNumber(counterId.toString())
				&& Objects.nonNull(tokenStatus)) {
			Optional<TokenDTO> tokenDTO = null;
			try {
				tokenDTO = tokenDAO.getTokenByStatusAndCounter(counterId, tokenStatus);
				if (tokenDTO.isPresent()) {
					TokenDTO token = tokenDTO.get();
					if (Objects.nonNull(token)) {
						token = this.setCustomerDetailsForToken(token);
					}
					return token;
				}
			} catch (NullPointerException nullPointerException) {
				return null;
			}
		}
		return null;
	}
	
	private TokenDTO setCustomerDetailsForToken(TokenDTO tokenDTO) {
		try {
			if (Objects.nonNull(tokenDTO) && Objects.nonNull(tokenDTO.getCustomer())
					&& Objects.nonNull(tokenDTO.getCustomer().getId())
					&& validatorService.isValidNumber(tokenDTO.getCustomer().getId().toString())) {
				tokenDTO.setCustomer(customerUtility.getCustomerDetailsById(tokenDTO.getCustomer().getId()));
			}
		} catch (NullPointerException nullPointerException) {
			return tokenDTO;
		}
		return tokenDTO;
	}

}
