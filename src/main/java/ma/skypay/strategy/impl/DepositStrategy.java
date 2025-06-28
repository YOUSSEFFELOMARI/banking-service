package ma.skypay.strategy.impl;

import ma.skypay.model.Account;
import ma.skypay.model.Transaction;
import ma.skypay.strategy.TransactionStrategy;

import java.time.LocalDateTime;

public class DepositStrategy implements TransactionStrategy {

    @Override
    public void execute(Account account, int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }

        synchronized (account.getLock()) {
            LocalDateTime dateTime = LocalDateTime.now();
            int newBalance = account.getBalance().addAndGet(amount);
            account.getTransactions().add(new Transaction(dateTime, amount, newBalance));
        }
    }
}
