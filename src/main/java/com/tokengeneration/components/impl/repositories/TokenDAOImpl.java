package com.tokengeneration.components.impl.repositories;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.tokengeneration.components.repositories.TokenDAO;
import com.tokengeneration.domains.dto.TokenDTO;
import com.tokengeneration.domains.dto.mappers.TokenDTOMapper;

@Repository
public class TokenDAOImpl implements TokenDAO {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Optional<Long> createNewToken(TokenDTO tokenDTO) {
		Supplier<Long> id = null;
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("customer_id", tokenDTO.getCustomer().getId());
		args.put("token_code", tokenDTO.getTokenCode());
		args.put("status", tokenDTO.getStatus());
		args.put("created", tokenDTO.getCreated());
		args.put("updated", tokenDTO.getUpdated());
		args.put("present_foot_count", tokenDTO.getPresentFootCount());
		args.put("original_foot_count", tokenDTO.getOriginalFootCount());
		args.put("service_id", tokenDTO.getBankServiceId());
		args.put("branch_id", tokenDTO.getBranchId());
		try {
			id = () -> (Long) new SimpleJdbcInsert(dataSource).withSchemaName("turvo").withTableName("token")
					.usingColumns("customer_id", "token_code", "status", "created", "updated", "present_foot_count",
							"original_foot_count", "service_id", "branch_id")
					.usingGeneratedKeyColumns("id").withoutTableColumnMetaDataAccess().executeAndReturnKey(args);
		} catch (NullPointerException nullPointerException) {
			return Optional.empty();
		} catch (DuplicateKeyException duplicateKeyException) {
			return Optional.empty();
		} catch (InvalidDataAccessApiUsageException invalidDataAccessApiUsageException) {
			return Optional.empty();
		} catch (Exception exception) {
			return Optional.empty();
		}
		return Optional.ofNullable(id.get());
	}

	@Override
	public Optional<TokenDTO> getTokenByStatusAndCounter(Long counterId, String tokenStatus) {
		Supplier<TokenDTO> tokenDTO = null;
		Object[] parameters = new Object[2];
		parameters[0] = counterId;
		parameters[1] = tokenStatus;
		try {
			tokenDTO = () -> jdbcTemplate.queryForObject(
					"select * from turvo.token where counter_id = ? and status = ? order by updated asc limit 1",
					parameters, new TokenDTOMapper());
		} catch (NullPointerException nullPointerException) {
			return Optional.empty();
		} catch (EmptyResultDataAccessException emptyResultDataAccessException) {
			return Optional.empty();
		} catch (IncorrectResultSizeDataAccessException incorrectResultSizeDataAccessException) {
			return Optional.empty();
		} catch (Exception exception) {
			return Optional.empty();
		}
		return Optional.ofNullable(tokenDTO.get());
	}

	@Override
	public Optional<TokenDTO> fetchNewToken(Long branchId, Long serviceId, String tokenStatus) {
		Supplier<TokenDTO> tokenDTO = null;
		Object[] parameters = new Object[3];
		parameters[0] = branchId;
		parameters[1] = tokenStatus;
		parameters[2] = serviceId;
		try {
			tokenDTO = () -> jdbcTemplate.queryForObject(
					"select * from turvo.token where branch_id = ? and status = ?  and service_id = ? order by present_foot_count asc limit 1",
					parameters, new TokenDTOMapper());
		} catch (NullPointerException nullPointerException) {
			return Optional.empty();
		} catch (EmptyResultDataAccessException emptyResultDataAccessException) {
			return Optional.empty();
		} catch (IncorrectResultSizeDataAccessException incorrectResultSizeDataAccessException) {
			return Optional.empty();
		} catch (Exception exception) {
			return Optional.empty();
		}
		return Optional.ofNullable(tokenDTO.get());
	}

	@Override
	public Optional<Integer> setTokenStatusAndCounter(Long tokenId, Long counterId, String tokenStatus) {
		Object[] parameters = new Object[3];
		parameters[0] = counterId;
		parameters[1] = tokenStatus;
		parameters[2] = tokenId;
		String sql = "update turvo.token set counter_id = ?, status = ? where id = ?";
		Supplier<Integer> numberOfRowsAffected;
		try {
			numberOfRowsAffected = () -> jdbcTemplate.update(sql, parameters);
		} catch (Exception exception) {
			exception.printStackTrace();
			return Optional.empty();
		}
		return Optional.ofNullable(numberOfRowsAffected.get());
	}

	@Override
	public Optional<Integer> changeTokenStatus(String tokenId, String requestedStatus) {
		Object[] parameters = new Object[2];
		parameters[0] = requestedStatus;
		parameters[1] = tokenId;
		String sql = "update turvo.token set status = ? where id = ?";
		Supplier<Integer> numberOfRowsAffected;
		try {
			numberOfRowsAffected = () -> jdbcTemplate.update(sql, parameters);
		} catch (Exception exception) {
			exception.printStackTrace();
			return Optional.empty();
		}
		return Optional.ofNullable(numberOfRowsAffected.get());
	}

	@Override
	public Optional<Integer> changeTokenCounter(String tokenId, String nextCounterId) {
		Object[] parameters = new Object[2];
		parameters[0] = nextCounterId;
		parameters[1] = tokenId;
		String sql = "update turvo.token set counter_id = ? where id = ?";
		Supplier<Integer> numberOfRowsAffected;
		try {
			numberOfRowsAffected = () -> jdbcTemplate.update(sql, parameters);
		} catch (Exception exception) {
			exception.printStackTrace();
			return Optional.empty();
		}
		return Optional.ofNullable(numberOfRowsAffected.get());
	}

	@Override
	public Optional<TokenDTO> getTokenDetails (Long id) {
		Supplier<TokenDTO> tokenDTO = null;
		Object[] parameters = new Object[1];
		parameters[0] = id;
		try {
			tokenDTO = () -> jdbcTemplate.queryForObject("select * from turvo.token where id = ?",
					parameters, new TokenDTOMapper());
		} catch (NullPointerException nullPointerException) {
			return Optional.empty();
		} catch (EmptyResultDataAccessException emptyResultDataAccessException) {
			return Optional.empty();
		} catch (IncorrectResultSizeDataAccessException incorrectResultSizeDataAccessException) {
			return Optional.empty();
		} catch (Exception exception) {
			return Optional.empty();
		}
		return Optional.ofNullable(tokenDTO.get());
	}

}
