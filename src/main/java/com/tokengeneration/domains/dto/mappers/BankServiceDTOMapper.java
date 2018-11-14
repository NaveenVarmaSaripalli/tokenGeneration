package com.tokengeneration.domains.dto.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.tokengeneration.domains.dto.BankServiceDTO;

public class BankServiceDTOMapper implements RowMapper<BankServiceDTO> {

	@Override
	public BankServiceDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		BankServiceDTO bankServiceDTO = new BankServiceDTO();
		bankServiceDTO.setId(rs.getLong("service_id"));
		bankServiceDTO.setName(rs.getString("service_name"));
		bankServiceDTO.setBranchId(rs.getLong("branch_id"));
		bankServiceDTO.setPreviligedFootCount(rs.getInt("previliged_foot_count"));
		bankServiceDTO.setNonPreviligedFootCount(rs.getInt("non_previliged_foot_count"));
		return bankServiceDTO;
	}

}
