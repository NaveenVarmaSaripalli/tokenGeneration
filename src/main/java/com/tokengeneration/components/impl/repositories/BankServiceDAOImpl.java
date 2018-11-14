package com.tokengeneration.components.impl.repositories;

import java.util.Optional;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.tokengeneration.components.repositories.BankServiceDAO;
import com.tokengeneration.domains.dto.BankServiceDTO;
import com.tokengeneration.domains.dto.mappers.BankServiceDTOMapper;

@Repository
public class BankServiceDAOImpl implements BankServiceDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Optional<BankServiceDTO> getServiceDetails(Long serviceId, Long branchId) {
		Supplier<BankServiceDTO> branchDTO = null;
		Object[] parameters = new Object[2];
		parameters[0] = serviceId;
		parameters[1] = branchId;
		try {
			branchDTO = () -> jdbcTemplate.queryForObject(
					"SELECT service.id service_id, service.name service_name, branch_service.branch_id, branch_service.previliged_foot_count, branch_service.non_previliged_foot_count FROM turvo.service service inner join turvo.branch_service branch_service where service.id = ? and branch_id = ?",
					parameters, new BankServiceDTOMapper());
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
	public Optional<Integer> incrementPreviligedFootCount(Long serviceId, Long branchId) {
		String sql = "UPDATE turvo.branch_service set previliged_foot_count  = previliged_foot_count + 1 where branch_id = ? and service_id = ?";
		return this.incrementrequestedFootCount(sql, serviceId, branchId);
	}

	@Override
	public Optional<Integer> incrementNonPreviligedFootCount(Long serviceId, Long branchId) {
		String sql = "UPDATE turvo.branch_service set non_previliged_foot_count  = non_previliged_foot_count + 1 where branch_id = ? and service_id = ?";
		return this.incrementrequestedFootCount(sql, serviceId, branchId);
	}

	private Optional<Integer> incrementrequestedFootCount(String sql, Long serviceId, Long branchId) {
		Object[] parameters = new Object[2];
		parameters[0] = branchId;
		parameters[1] = serviceId;
		Supplier<Integer> NumberOfRowsAffected;
		try {
			NumberOfRowsAffected = () -> jdbcTemplate.update(sql, parameters);
		} catch (Exception exception) {
			exception.printStackTrace();
			return Optional.empty();
		}
		return Optional.ofNullable(NumberOfRowsAffected.get());
	}

}
