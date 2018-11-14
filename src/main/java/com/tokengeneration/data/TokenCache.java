package com.tokengeneration.data;

import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentHashMap;

import com.tokengeneration.domains.dto.TokenDTO;

public final class TokenCache {
	
	private Map<String, PriorityQueue<TokenDTO>> tokenMap;


	private TokenCache() {
		this.tokenMap = new ConcurrentHashMap<String, PriorityQueue<TokenDTO>>();
	}
	
	public static TokenCache getInstance() {
		return TokenDataCache.tokenCache;
	}
	
	private Map<String, PriorityQueue<TokenDTO>> getTokenMap() {
		return this.tokenMap;
	}
	
	private static class TokenDataCache {
		private static TokenCache tokenCache = new TokenCache();
	}
	
	public PriorityQueue<TokenDTO> get(String key) {
		return this.tokenMap.get(key);
	}
}
