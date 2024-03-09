package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Balance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcAccountDao implements AccountDao{


    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Balance getBalanceByUserId(int userId) {
        String sql = "SELECT balance FROM account WHERE user_id = ?";
        Balance balance = new Balance();
        try {
            balance.setBalance(jdbcTemplate.queryForObject(sql, BigDecimal.class, userId));
        } catch (DataAccessException e) {
            System.out.println(e.getMessage());
        }
        return balance;
    }

    @Override
    public Balance getBalanceByAccountId(int accountId) {
        String sql = "SELECT balance FROM account WHERE account_id = ?";
        Balance balance = new Balance();
        try {
            balance.setBalance(jdbcTemplate.queryForObject(sql, BigDecimal.class, accountId));
        } catch (DataAccessException e) {
            System.out.println(e.getMessage());
        }
        return balance;
    }

    @Override
    public Account getAccountByAccountId(int accountId) {
        String sql = "SELECT account_id, user_id, balance FROM account WHERE account_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, accountId);
        Account account = null;
        if(result.next()) {
            account = mapResultsToAccount(result);
        }
        return account;
    }

    @Override
    public Account getAccountByUserId(int userId) {
        String sql = "SELECT account_id, user_id, balance FROM account WHERE user_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId);
        Account account = null;
        if(result.next()) {
            account = mapResultsToAccount(result);
        }
        return account;
    }

    @Override
    public List<Account> findAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT user_id, username, password_hash FROM users;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()) {
            Account account = mapResultsToAccount(results);
            accounts.add(account);
        }
        return accounts;
    }

    @Override
    public void updateBalance(int id, Balance amount) {
        String sql = "UPDATE account " +
                "SET balance = ? " +
                "WHERE account_id = ?";
        BigDecimal balance = amount.getBalance();
        jdbcTemplate.update(sql, balance, id);
    }

    private Account mapResultsToAccount(SqlRowSet result) {
        int id = result.getInt("account_id");
        int userId = result.getInt("user_id");

        Balance balance = new Balance();
        String accountBalance = result.getString("balance");
        balance.setBalance(new BigDecimal(accountBalance));
        return new Account(id, userId, balance);
    }
}
