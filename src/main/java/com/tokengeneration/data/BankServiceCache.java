package com.tokengeneration.data;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

import com.tokengeneration.domains.dto.BankServiceDTO;

public final class BankServiceCache {

	private Map<String,BankServiceDTO> bankServices;
	
	private BankServiceCache() {
		this.bankServices = new ConcurrentHashMap<String,BankServiceDTO>();
	}
	
	public static BankServiceCache getInstance() {
		return BankServiceCacheData.bankServiceCache;
	}

	
	private Map<String,BankServiceDTO> getBankServices() {
		return bankServices;
	}

	public synchronized void put(BankServiceDTO bankServiceDTO) {
		Optional<BankServiceDTO> bankService = this.get(bankServiceDTO.getId(),bankServiceDTO.getBranchId());
		if(!bankService.isPresent()) {
			this.getBankServices().put(bankServiceDTO.getId()+"-"+bankServiceDTO.getBranchId(), bankServiceDTO);
		}
	}
	
	public Optional<BankServiceDTO> get(Long serviceId, Long branchId) {
		Supplier<BankServiceDTO> bankService = () -> this.getBankServices().get(serviceId+"-"+branchId);
		return Optional.ofNullable(bankService.get());
	}
	
	private static class BankServiceCacheData {
		public static BankServiceCache bankServiceCache = new BankServiceCache();
	}
	
	public BankServiceCache clone() {
		return getInstance();
	}

}
