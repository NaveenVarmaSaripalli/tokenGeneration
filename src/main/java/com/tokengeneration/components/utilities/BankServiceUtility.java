package com.tokengeneration.components.utilities;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tokengeneration.components.repositories.BankServiceDAO;
import com.tokengeneration.components.services.ValidatorService;
import com.tokengeneration.data.BankServiceCache;
import com.tokengeneration.domains.dto.BankServiceDTO;

@Component
public class BankServiceUtility {

	@Autowired
	BankServiceDAO bankServiceDAO;

	@Autowired
	private ValidatorService validatorService;

	private BankServiceCache bankServiceCache = BankServiceCache.getInstance();

	public synchronized boolean incrementPreviligedFootCount(long serviceId, long branchId) {
		Optional<Integer> numberOfRowsAffected = null;
		if (Objects.nonNull(serviceId) && Objects.nonNull(branchId)) {
			numberOfRowsAffected = bankServiceDAO.incrementPreviligedFootCount(serviceId, branchId);
		}
		if (numberOfRowsAffected.isPresent()) {
			return true;
		} else {
			return false;
		}
	}

	public synchronized boolean incrementNonPreviligedFootCount(long serviceId, long branchId) {
		Optional<Integer> numberOfRowsAffected = null;
		if (Objects.nonNull(serviceId) && Objects.nonNull(branchId)) {
			numberOfRowsAffected = bankServiceDAO.incrementNonPreviligedFootCount(serviceId, branchId);
		}
		if (numberOfRowsAffected.isPresent()) {
			return true;
		} else {
			return false;
		}
	}

	@Transactional
	public BankServiceDTO getBankServiceDTO(Long serviceId, Long branchId) {
		Optional<BankServiceDTO> bankServiceDTO;
		if (Objects.nonNull(serviceId) && validatorService.isValidNumber(serviceId.toString())
				&& Objects.nonNull(branchId) && validatorService.isValidNumber(branchId.toString())) {
			bankServiceDTO = bankServiceCache.get(serviceId, branchId);
			if (bankServiceDTO.isPresent()) {
				return bankServiceDTO.get();
			} else {
				bankServiceDTO = bankServiceDAO.getServiceDetails(serviceId, branchId);
				if (bankServiceDTO.isPresent()) {
					bankServiceCache.put(bankServiceDTO.get());
					return bankServiceDTO.get();
				}
			}
		}
		return null;
	}
}