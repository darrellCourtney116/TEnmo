package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Balance;

import java.math.BigDecimal;
import java.util.List;

public interface AccountDao {
    Balance getBalanceByUserId(int userId);
    Balance getBalanceByAccountId(int accountId);
    Account getAccountByAccountId(int accountId);
    Account getAccountByUserId(int userId);
    List<Account> findAllAccounts();
    void updateBalance(int id, Balance amount);
}
