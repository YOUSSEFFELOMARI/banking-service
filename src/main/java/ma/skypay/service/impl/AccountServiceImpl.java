package ma.skypay.service.impl;

import lombok.Getter;
import ma.skypay.model.Account;
import ma.skypay.service.AccountService;
import ma.skypay.strategy.TransactionStrategy;
import ma.skypay.strategy.impl.DepositStrategy;
import ma.skypay.strategy.impl.WithdrawStrategy;

import java.time.format.DateTimeFormatter;

public class AccountServiceImpl implements AccountService {
    @Getter
    private final Account account ;

    public AccountServiceImpl() {
        this.account = new Account("userName","userLastName");
    }

    public AccountServiceImpl(Account account) {
        this.account = account;
    }


    @Override
    public void deposit(int amount) {
        TransactionStrategy transactionStrategy = new DepositStrategy();
        transactionStrategy.execute(account, amount);
    }

    @Override
    public void withdraw(int amount) {
        TransactionStrategy transactionStrategy = new WithdrawStrategy();
        transactionStrategy.execute(account, amount);
    }

    @Override
    public void printStatement() {

        System.out.println("    DATE    |   AMOUNT  |  BALANCE");
        account.getTransactions().stream()
                .sorted((t1, t2) -> t2.getDate().compareTo(t1.getDate()))
                .forEach(t -> {
                    String formatingSpace = t.getAmount() > 0 ? " " : "";
                    System.out.printf("%s  |  %s%d     |  %d\n",
                            t.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE), formatingSpace, t.getAmount(), t.getBalanceAfterTransaction());
                });
    }

}
