package com.tokengeneration.components.impl.repositories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import com.tokengeneration.components.repositories.CustomerServiceDAO;
import com.tokengeneration.domains.dto.CustomerDTO;
import com.tokengeneration.domains.dto.mappers.CustomerDTOMapper;

@Repository
public class CustomerServiceDAOImpl implements CustomerServiceDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private DataSource dataSource;

	@Override
	@Nullable
	public Optional<CustomerDTO> getCustomerDetails(Long phoneNumber) {
		Supplier<CustomerDTO> customer = null;
		Object[] parameters = new Object[1];
		parameters[0] = phoneNumber;
		try {
			customer = () -> jdbcTemplate.queryForObject("select * from turvo.customer where contact_number = ?", parameters,
					new CustomerDTOMapper());
		} catch (NullPointerException nullPointerException) {
			return Optional.empty();
		} catch (EmptyResultDataAccessException emptyResultDataAccessException) {
			return Optional.empty();
		} catch (IncorrectResultSizeDataAccessException incorrectResultSizeDataAccessException) {
			return Optional.empty();
		} catch (Exception exception) {
			return Optional.empty();
		}
		return Optional.ofNullable(customer.get());
	}

	@Override
	public Optional<Long> createNewCustomer(CustomerDTO newCustomer) {
		Supplier<Long> id = null;
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("name", newCustomer.getName());
		args.put("contact_number", newCustomer.getPhoneNumber());
		args.put("home_branch_id", newCustomer.getHomeBranchId());
		try {
			id = () -> (Long) new SimpleJdbcInsert(dataSource).withSchemaName("turvo").withTableName("customer")
					.usingColumns("name", "contact_number", "home_branch_id").usingGeneratedKeyColumns("id")
					.withoutTableColumnMetaDataAccess().executeAndReturnKey(args);
		} catch (NullPointerException nullPointerException) {
			return Optional.empty();
		} catch (DuplicateKeyException duplicateKeyException) {
			return Optional.empty();
		} catch(InvalidDataAccessApiUsageException InvalidDataAccessApiUsageException) {
			return Optional.empty();
		} catch (Exception exception) {
			return Optional.empty();
		}
		return Optional.ofNullable(id.get());
	}

	@Override
	public Optional<CustomerDTO> getCustomerDetailsById(Long id) {
		Supplier<CustomerDTO> customer = null;
		Object[] parameters = new Object[1];
		parameters[0] = id;
		try {
			customer = () -> jdbcTemplate.queryForObject("select * from turvo.customer where id = ?", parameters,
					new CustomerDTOMapper());
		} catch (NullPointerException nullPointerException) {
			return Optional.empty();
		} catch (EmptyResultDataAccessException emptyResultDataAccessException) {
			return Optional.empty();
		} catch (IncorrectResultSizeDataAccessException incorrectResultSizeDataAccessException) {
			return Optional.empty();
		} catch (Exception exception) {
			return Optional.empty();
		}
		return Optional.ofNullable(customer.get());
	}
	
	@Override
	public Optional<List<CustomerDTO>> getCustomerDetails() {
		Supplier<List<CustomerDTO>> customers = null;
		try {
			customers = () -> jdbcTemplate.query("select * from turvo.customer", new CustomerDTOMapper());
		} catch (NullPointerException nullPointerException) {
			return Optional.empty();
		} catch (DataAccessException dataAccessException) {
			return Optional.empty();
		} catch (Exception exception) {
			return Optional.empty();
		}
		return Optional.ofNullable(customers.get());
	}

}
