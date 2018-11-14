package com.tokengeneration.components.utilities;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tokengeneration.components.repositories.BranchServiceDAO;
import com.tokengeneration.components.services.ValidatorService;
import com.tokengeneration.data.BranchCache;
import com.tokengeneration.domains.dto.BranchDTO;

@Component
public class BranchUtility {

	@Autowired
	private BranchServiceDAO branchServiceDAO;

	@Autowired
	private ValidatorService validatorService;
	
	private BranchCache branchCache = BranchCache.getInstance();

	public BranchDTO getBranchDetails(Long branchId) {
		if (Objects.nonNull(branchId) && validatorService.isValidNumber(branchId.toString())) {
			Optional<BranchDTO> branch = branchCache.get(branchId);
			if (branch.isPresent()) {
				return branch.get();
			} else {
				Optional<BranchDTO> branchDTO = branchServiceDAO.getBranchDetailsById(branchId);
				if (branchDTO.isPresent()) {
					branchCache.put(branchDTO.get());
					return branchDTO.get();
				}
			}
		}
		return null;
	}

}
