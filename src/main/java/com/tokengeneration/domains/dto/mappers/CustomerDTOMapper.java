package com.tokengeneration.domains.dto.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.tokengeneration.domains.dto.CustomerDTO;

public class CustomerDTOMapper implements RowMapper<CustomerDTO> {

	@Override
	public CustomerDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		boolean isValuedCustomer = false;
		if ("Y".equalsIgnoreCase(rs.getString("is_valued"))) {
			isValuedCustomer = true;
		}
		CustomerDTO customer = new CustomerDTO(rs.getLong("id"), rs.getString("name"), rs.getString("contact_number"), isValuedCustomer,
				rs.getString("home_branch_id"));
		return customer;
	}

}
