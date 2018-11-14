package com.tokengeneration.components.impl.repositories;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.tokengeneration.components.repositories.BranchServiceDAO;
import com.tokengeneration.domains.dto.BranchDTO;
import com.tokengeneration.domains.dto.mappers.BranchDTOMapper;

@Repository
public class BranchServiceDAOImpl implements BranchServiceDAO{

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public Optional<BranchDTO> getBranchDetailsById (Long branchId) {
		Supplier<BranchDTO> branchDTO = null;
		Object[] parameters = new Object[1];
		parameters[0] = branchId;
		try {
			branchDTO = () -> jdbcTemplate.queryForObject("select * from turvo.branch where id = ?", parameters,
					new BranchDTOMapper());
		} catch (EmptyResultDataAccessException emptyResultDataAccessException) {
			return Optional.empty();
		} catch (IncorrectResultSizeDataAccessException incorrectResultSizeDataAccessException) {
			return Optional.empty();
		} catch (Exception exception) {
			return Optional.empty();
		}
		return Optional.ofNullable(branchDTO.get());
	}
	
	@Override
	public Optional<List<BranchDTO>> getBranchDetails () {
		Supplier<List<BranchDTO>> branchList = null;
		try {
			branchList = () -> jdbcTemplate.query("select * from turvo.branch", new BranchDTOMapper());
		} catch (DataAccessException dataAccessException) {
			return Optional.empty();
		} catch (Exception exception) {
			return Optional.empty();
		}
		return Optional.ofNullable(branchList.get());
	}

}
