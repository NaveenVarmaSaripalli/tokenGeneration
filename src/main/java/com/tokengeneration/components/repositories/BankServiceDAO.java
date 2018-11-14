package com.tokengeneration.components.repositories;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.tokengeneration.domains.dto.BankServiceDTO;

@Component
public interface BankServiceDAO {

	public Optional<BankServiceDTO> getServiceDetails(Long serviceId, Long branchId);

	Optional<Integer> incrementPreviligedFootCount(Long serviceId, Long branchId);

	Optional<Integer> incrementNonPreviligedFootCount(Long serviceId, Long branchId);

}
