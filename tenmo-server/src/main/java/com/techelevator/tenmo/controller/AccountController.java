package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.*;
import com.techelevator.tenmo.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated")
public class AccountController {
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private TransferDao transferDao;
    @Autowired
    private UserDao userDao;

    @Autowired
    private TransferTypeDao transferTypeDao;
    @Autowired
    private TransferStatusDao transferStatusDao;



    @PreAuthorize("permitAll")
    @RequestMapping(path = "/balance/{id}", method = RequestMethod.GET)
    public Balance getBalance(User user) {
        return accountDao.getBalanceByUserId(user.getId());
    }

    @PreAuthorize("permitAll")
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/transfers/{id}", method = RequestMethod.POST)
    public void createTransfer(@Valid @RequestBody Transfer transfer) {
        // gets accounts for Transfer from ids
        Account account = accountDao.getAccountByAccountId(transfer.getAccountFrom());
        Account accountTo = accountDao.getAccountByAccountId(transfer.getAccountTo());
        // amount of money being transferred
        BigDecimal amount = transfer.getAmount();
        // does transfer
        account.getBalance().sendMoney(amount);
        accountTo.getBalance().receiveMoney(amount);
        transferDao.createTransfer(transfer);
        // updates account Balances
        accountDao.updateBalance(account.getId(), account.getBalance());
        accountDao.updateBalance(accountTo.getId(), accountTo.getBalance());
    }
    @PreAuthorize("permitAll")
    @RequestMapping(path="/account/user/{id}", method = RequestMethod.GET)
    public Account getAccountByUserId(@PathVariable int id) {
        return accountDao.getAccountByUserId(id);
    }

    @PreAuthorize("permitAll")
    @RequestMapping(path="/account/{id}", method = RequestMethod.GET)
    public Account getAccountByAccountId(@PathVariable int id) {
        return accountDao.getAccountByAccountId(id);
    }

    @PreAuthorize("permitAll")
    @RequestMapping(path="/transfers/{id}", method = RequestMethod.GET)
    public List<Transfer> getTransfersByUserId(@PathVariable int id) {
        return transferDao.getTransfersByUserId(id);
    }

    @PreAuthorize("permitAll")
    @RequestMapping(path="/transfers", method = RequestMethod.GET)
    public List<Transfer> getAllTransfers() {
        return transferDao.getAllTransfers();
    }

    @PreAuthorize("permitAll")
    @RequestMapping(path="/users", method = RequestMethod.GET)
    public List<User> getUsers() {
        return userDao.getUsers();
    }

    @PreAuthorize("permitAll")
    @RequestMapping(path="/users/{id}", method = RequestMethod.GET)
    public User getUserById(@PathVariable int id) {
        return userDao.getUserById(id);
    }

    @PreAuthorize("permitAll")
    @RequestMapping(path="/transfertype/{id}", method = RequestMethod.GET)
    public TransferType getTransferTypeById(@PathVariable int id) {
        return transferTypeDao.getTransferTypeById(id);
    }

    @PreAuthorize("permitAll")
    @RequestMapping(path="/transferstatus/{id}", method = RequestMethod.GET)
    public TransferStatus getTransferStatusById(@PathVariable int id) {
        return transferStatusDao.getTransferStatusById(id);
    }

}
