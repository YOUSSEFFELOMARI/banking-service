package ma.skypay;


import ma.skypay.service.AccountService;
import ma.skypay.service.impl.AccountServiceImpl;

import java.util.Random;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        AccountService accountService = new AccountServiceImpl();
        Random random = new Random();

        // 5 deposits
        IntStream.range(0, 5).forEach(i -> {
            int amount = (random.nextInt(9) + 1) * 100;
            accountService.deposit(amount);
        });

        // 3 withdrawals
        IntStream.range(0, 3).forEach(i -> {
            int amount = (random.nextInt(5) + 1) * 100;
            try {
                accountService.withdraw(amount);
            } catch (IllegalArgumentException e) {
                System.out.println("Withdrawal failed: " + e.getMessage());
            }
        });

        System.out.println("\nFinal Bank Statement:\n");
        accountService.printStatement();

    }
}