package ma.skypay.strategy;

import ma.skypay.model.Account;

public interface TransactionStrategy {
    void execute(Account account, int amount);
}