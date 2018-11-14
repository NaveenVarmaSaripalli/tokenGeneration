package com.tokengeneration.data;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

import com.tokengeneration.domains.dto.BranchDTO;

public final class BranchCache {

	private Map<Long, BranchDTO> branches;

	private BranchCache() {
		branches = new ConcurrentHashMap<Long, BranchDTO>();
	}

	public static BranchCache getInstance() {
		return BranchDataCache.branchCache;
	}

	private Map<Long, BranchDTO> getBranches() {
		return branches;
	}

	public Optional<BranchDTO> get(Long id) {
		Supplier<BranchDTO> branch = () -> this.getBranches().get(id);
		return Optional.ofNullable(branch.get());
	}

	public synchronized void put(BranchDTO branchDTO) {
		Optional<BranchDTO> branch = this.get(branchDTO.getId());
		if (!branch.isPresent()) {
			this.getBranches().put(branchDTO.getId(), branchDTO);
		}
	}

	private static class BranchDataCache {
		public static BranchCache branchCache = new BranchCache();
	}

	public BranchCache clone() {
		return getInstance();
	}

}
