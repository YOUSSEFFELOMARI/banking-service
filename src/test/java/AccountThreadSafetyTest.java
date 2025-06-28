import ma.skypay.model.Account;
import ma.skypay.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

class AccountThreadSafetyTest {

    @Test
    void testConcurrentDepositsAndWithdrawals() throws InterruptedException {
        Account account = new Account("Concurrent", "User");
        AccountServiceImpl accountService = new AccountServiceImpl(account);

        int threads = 100;
        ExecutorService executor = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(threads);

        List<Callable<Void>> tasks = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            tasks.add(() -> {
                accountService.deposit(100);
                latch.countDown();
                return null;
            });
        }
        for (int i = 0; i < 50; i++) {
            tasks.add(() -> {
                try {
                    accountService.withdraw(50);
                } catch (IllegalArgumentException ignored) {
                    // insufficient balance message will be thrown.
                }
                latch.countDown();
                return null;
            });
        }

        executor.invokeAll(tasks);
        latch.await();
        executor.shutdown();


        int expectedMinBalance = 0;
        int expectedMaxBalance = 5000;

        int actualBalance = account.getBalance().get();
        int txCount = account.getTransactions().size();

        System.out.println("Final balance: " + actualBalance);
        System.out.println("Transaction count: " + txCount);
        accountService.printStatement();

        assertTrue(actualBalance >= expectedMinBalance && actualBalance <= expectedMaxBalance);
        assertEquals(txCount, account.getTransactions().size());
    }
}