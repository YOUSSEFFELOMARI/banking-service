import ma.skypay.model.Account;
import ma.skypay.model.Transaction;
import ma.skypay.service.AccountService;
import ma.skypay.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceImplTest {

    private AccountServiceImpl accountService;

    @BeforeEach
    void setUp() {
        Account testAccount = new Account("Youssef", "SkyPay");
        accountService = new AccountServiceImpl(testAccount);
    }

    @Test
    void testDepositIncreasesBalance() {
        accountService.deposit(1000);
        Account account = accountService.getAccount();
        assertEquals(1000, account.getBalance().get());
        assertEquals(1, account.getTransactions().size());
    }

    @Test
    void testWithdrawDecreasesBalance() {
        accountService.deposit(1500);
        accountService.withdraw(500);

        Account account = accountService.getAccount();
        assertEquals(1000, account.getBalance().get());
        assertEquals(2, account.getTransactions().size());
    }

    @Test
    void testWithdrawThrowsWhenInsufficientFunds() {
        accountService.deposit(300);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            accountService.withdraw(1000);
        });

        String expectedMessage = "Insufficient balance";
        assertTrue(exception.getMessage().contains(expectedMessage));

        Account account = accountService.getAccount();
        assertEquals(300, account.getBalance().get()); // no withdrawal happened
        assertEquals(1, account.getTransactions().size());
    }

    @Test
    void testDepositWithZeroAmountThrows() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            accountService.deposit(0);
        });
        assertTrue(exception.getMessage().contains("Deposit amount must be positive"));
    }

    @Test
    void testWithdrawWithNegativeAmountThrows() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            accountService.withdraw(-100);
        });
        assertTrue(exception.getMessage().contains("Withdraw amount must be positive"));
    }
}