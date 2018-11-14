package com.tokengeneration.data;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

import com.tokengeneration.domains.dto.CounterDTO;

public final class CounterCache {

	private Map<Long, CounterDTO> counters;

	private CounterCache() {
		counters = new ConcurrentHashMap<Long, CounterDTO>();
	}

	public static CounterCache getInstance() {
		return CounterCacheData.counterCache;
	}

	public Map<Long, CounterDTO> getCounters() {
		return counters;
	}

	public Optional<CounterDTO> get(Long id) {
		Supplier<CounterDTO> counter = () -> this.getCounters().get(id);
		return Optional.ofNullable(counter.get());
	}

	public synchronized void put(CounterDTO counterDTO) {
		Optional<CounterDTO> counter = this.get(counterDTO.getId());
		if (!counter.isPresent()) {
			this.getCounters().put(counterDTO.getId(), counterDTO);
		}
	}

	private static class CounterCacheData {
		public static CounterCache counterCache = new CounterCache();
	}

	public CounterCache clone() {
		return getInstance();
	}

	
}
