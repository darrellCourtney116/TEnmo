package com.techelevator.tenmo.model;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class Transfer {
    @NotNull(message = "id should not be blank")
    private int id;
    @NotNull(message = "transferTypeId should not be blank")
    private int transferTypeId;
    @NotNull(message = "transferStatusId should not be blank")
    private int transferStatusId;
    @NotNull(message = "accountFrom should not be blank")
    private int accountFrom;
    @NotNull(message = "amountTo should not be blank")
    private int accountTo;
    @NotNull(message = "amount should not be blank")
    private BigDecimal amount;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTransferTypeId() {
        return transferTypeId;
    }

    public void setTransferTypeId(int transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    public int getTransferStatusId() {
        return transferStatusId;
    }

    public void setTransferStatusId(int transferStatusId) {
        this.transferStatusId = transferStatusId;
    }

    public int getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(int accountFrom) {
        this.accountFrom = accountFrom;
    }

    public int getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(int accountTo) {
        this.accountTo = accountTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Transfer(int id, int transferTypeId, int transferStatusId, int accountFrom, int accountTo, BigDecimal amount) {
        this.id = id;
        this.transferTypeId = transferTypeId;
        this.transferStatusId = transferStatusId;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
    }
}
