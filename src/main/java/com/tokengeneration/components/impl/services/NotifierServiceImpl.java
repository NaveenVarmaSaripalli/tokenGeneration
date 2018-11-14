package com.tokengeneration.components.impl.services;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.tokengeneration.components.services.NotifierService;
import com.tokengeneration.data.TokenCache;

@Service
public class NotifierServiceImpl implements NotifierService{

	private TokenCache tokenCache = TokenCache.getInstance();
	
	@Override
	public void reArrangeTokens(Long branchId, Long bankServiceId) {
		
		if(Objects.nonNull(tokenCache)) {/*
			PriorityQueue<TokenDTO> tokensArray = tokenCache.get(branchId+"-"+bankServiceId);
			if(Objects.isNull(tokensArray)) {
				tokenCache.put(branchId+"-"+bankServiceId, new PriorityQueue<TokenDTO>());
				tokensArray = tokenCache.get(branchId+"-"+bankServiceId);
			}
			tokensArray.add(token);
			bankService.setTokenQueue(tokensArray);
			tokenCache.put(branchId+"-"+bankServiceId, bankService.reArrangeTokens());
			for (TokenDTO tokenDTO : tokenCache.get(branchId+"-"+bankServiceId)) {
				System.out.println(tokenDTO.getId()+"-"+tokenDTO.getOriginalFootCount()+"-"+tokenDTO.getPresentFootCount());
			}
		*/}
	}
	
}
