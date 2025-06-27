package ma.skypay.strategy;

import ma.skypay.model.Account;

import java.time.LocalDate;

public interface TransactionStrategy {
    void execute(Account account, int amount, LocalDate date);
}