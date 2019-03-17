package com.example.spring.batch.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.spring.batch.model.Inventory;

public class InventoryRowMapper implements RowMapper<Inventory> {

	@Override
	public Inventory mapRow(ResultSet rs, int rowNumber) throws SQLException {
		Inventory inventory = new Inventory();
		
		inventory.setId(rs.getInt("id"));
		inventory.setName(rs.getString("name"));
		inventory.setQuantity(rs.getInt("quantity"));
		
		return inventory;
	}

}
