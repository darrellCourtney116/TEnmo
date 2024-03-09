package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.TransferStatus;
import com.techelevator.tenmo.model.TransferType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class JdbcTransferStatusDao implements TransferStatusDao{
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTransferStatusDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public TransferStatus getTransferStatusByDescription(String description) {
        String sql = "SELECT transfer_status_id, transfer_status_desc FROM transfer_status WHERE transfer_status_desc = ?";

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, description);
        TransferStatus transferStatus = null;
        if(result.next()) {
            transferStatus = mapResultsToTransferStatus(result);
        }
        return transferStatus;
    }

    @Override
    public TransferStatus getTransferStatusById(int id) {
        String sql = "SELECT transfer_status_id, transfer_status_desc FROM transfer_status WHERE transfer_status_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id);
        TransferStatus transferStatus = null;
        if(result.next()) {
            transferStatus = mapResultsToTransferStatus(result);
        }
        return transferStatus;
    }

    private TransferStatus mapResultsToTransferStatus(SqlRowSet result) {
        int id = result.getInt("transfer_status_id");
        String description = result.getString("transfer_status_desc");

        return new TransferStatus(id, description);
    }

}

