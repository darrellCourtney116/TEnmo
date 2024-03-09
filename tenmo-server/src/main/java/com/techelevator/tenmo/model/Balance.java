package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Balance {
        private BigDecimal balance;

        public BigDecimal getBalance() {
            return balance;
        }

        public void setBalance(BigDecimal balance) {
            this.balance = balance;
        }

    public void sendMoney(BigDecimal amount) {
        BigDecimal newBalance = new BigDecimal(String.valueOf(balance)).subtract(amount);
            this.balance = newBalance;
    }

    public void receiveMoney(BigDecimal amount) {
        this.balance = new BigDecimal(String.valueOf(balance)).add(amount);
    }
}
