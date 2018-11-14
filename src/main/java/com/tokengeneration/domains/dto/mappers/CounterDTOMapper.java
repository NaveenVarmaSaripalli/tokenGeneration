package com.tokengeneration.domains.dto.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.tokengeneration.domains.dto.CounterDTO;

public class CounterDTOMapper implements RowMapper<CounterDTO> {

	@Override
	public CounterDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		CounterDTO counterDTO = new CounterDTO(rs.getLong("id"), rs.getLong("service_id"), rs.getLong("branch_id"),
				rs.getString("name"), rs.getString("status"));
		return counterDTO;
	}

}
