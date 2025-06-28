package ma.skypay.strategy.impl;

import ma.skypay.model.Account;
import ma.skypay.model.Transaction;
import ma.skypay.strategy.TransactionStrategy;

import java.time.LocalDateTime;

public class WithdrawStrategy implements TransactionStrategy {

    @Override
    public void execute(Account account, int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdraw amount must be positive");
        }

        synchronized (account.getLock()) {
            LocalDateTime dateTime = LocalDateTime.now();
            int currentBalance = account.getBalance().get();
            if (currentBalance < amount) {
                throw new IllegalArgumentException("Insufficient balance");
            }
            int newBalance = account.getBalance().addAndGet(-amount);
            account.getTransactions().add(new Transaction(dateTime, -amount, newBalance));
        }
    }
}
