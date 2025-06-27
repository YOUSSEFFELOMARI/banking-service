package ma.skypay.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class Account {

    @Setter
    private String name;
    @Setter
    private String lastName;
    private final List<Transaction> transactions ;
    private final AtomicInteger balance ;

    //Lock object for thread safe operations
    private final Object lock = new Object();

    public Account(String name, String lastName) {
        this.transactions = new CopyOnWriteArrayList<>();
        this.balance = new AtomicInteger(0);
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }
}