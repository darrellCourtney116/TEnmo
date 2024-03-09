package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Balance;

import java.math.BigDecimal;

public interface AccountService {
    BigDecimal getBalance();
    Account getAccountByUserId(int userId);
    Account getAccountById(int id);

}
