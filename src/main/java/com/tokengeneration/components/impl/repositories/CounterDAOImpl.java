package com.tokengeneration.components.impl.repositories;

import java.util.Optional;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.tokengeneration.components.repositories.CounterDAO;
import com.tokengeneration.domains.dto.CounterDTO;
import com.tokengeneration.domains.dto.mappers.CounterDTOMapper;

@Repository
public class CounterDAOImpl implements CounterDAO{

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public Optional<CounterDTO> getCounterDTO(Long counterId) {
		Supplier<CounterDTO> counterDTO = null;
		Object[] parameters = new Object[1];
		parameters[0] = counterId;
		try {
			counterDTO = () -> jdbcTemplate.queryForObject("select * from turvo.counter where id = ?", parameters, new CounterDTOMapper());
		} catch(NullPointerException nullPointerException) {
			return Optional.empty();
		} catch (EmptyResultDataAccessException emptyResultDataAccessException) {
			return Optional.empty();
		} catch (IncorrectResultSizeDataAccessException incorrectResultSizeDataAccessException) {
			return Optional.empty();
		} catch (Exception exception) {
			return Optional.empty();
		}
		return Optional.ofNullable(counterDTO.get());
	}

}
