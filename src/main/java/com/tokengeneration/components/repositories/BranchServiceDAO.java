package com.tokengeneration.components.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.tokengeneration.domains.dto.BranchDTO;

@Component
public interface BranchServiceDAO {

	public Optional<BranchDTO> getBranchDetailsById (Long branchId);

	public Optional<List<BranchDTO>> getBranchDetails();

}
