package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.TransferType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class JdbcTransferTypeDao implements TransferTypeDao{


    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTransferTypeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public TransferType getTransferTypeById(int id) {
        String sql = "SELECT transfer_type_id, transfer_type_desc FROM transfer_type WHERE transfer_type_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id);

        TransferType transferType = null;
        if(result.next()) {
            transferType = mapResultsToTransferType(result);
        }


        return transferType;
    }

    @Override
    public TransferType getTransferTypeFromDescription(String description) {
        String sql = "SELECT transfer_type_id, transfer_type_desc FROM transfer_type WHERE transfer_type_desc = ?";

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, description);
        TransferType transferType = null;

        if(result.next()) {
            transferType = mapResultsToTransferType(result);
        }
        return transferType;

    }

    private TransferType mapResultsToTransferType(SqlRowSet result) {
        int id = result.getInt("transfer_type_id");
        String description = result.getString("transfer_type_desc");

        return new TransferType(id, description);
    }
}