package com.tokengeneration.domains.dto.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.tokengeneration.domains.dto.BranchDTO;

public class BranchDTOMapper implements RowMapper<BranchDTO>{

	@Override
	public BranchDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		BranchDTO branchDTO = new BranchDTO();
		
		branchDTO.setId(rs.getLong("id"));
		branchDTO.setLocation(rs.getString("location"));
		branchDTO.setBranchCode(rs.getString("branch_code"));
		
		return branchDTO;
	}

}
