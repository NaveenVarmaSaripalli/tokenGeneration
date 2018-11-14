package com.tokengeneration.domains.dto.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.tokengeneration.domains.dto.CustomerDTO;
import com.tokengeneration.domains.dto.TokenDTO;

public class TokenDTOMapper implements RowMapper<TokenDTO> {

	@Override
	public TokenDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		CustomerDTO customerDTO = new CustomerDTO(rs.getLong("customer_id"), null, null, false, null);
		TokenDTO tokenDTO = new TokenDTO(rs.getLong("id"), customerDTO, rs.getLong("counter_id"), rs.getLong("service_id"),
				rs.getLong("branch_id"), rs.getString("token_code"), rs.getString("status"), rs.getTimestamp("created"),
				rs.getTimestamp("updated"), rs.getInt("original_foot_count"), rs.getInt("present_foot_count"));

		return tokenDTO;
	}

}
