#  Skypay Banking Service - Technical Test

A simple banking system developed in Java as part of a technical test.  
It supports core features like:

- ✅ Depositing money
- ✅ Withdrawing money
- ✅ Printing transaction statements

This project is focused on writing clean, testable, thread-safe code while applying design patterns like the **Strategy Pattern**.

## 📁 Project Structure

```
src/main/java/ma/skypay/
├── model/                # Domain models (Account, Transaction)
├── service/              # AccountService interface
├── service/impl/         # AccountServiceImpl with business logic
├── strategy/             # TransactionStrategy interface
└── strategy/impl/        # Deposit and Withdraw strategy implementations

src/test/java/ma/skypay/
├── AccountServiceImplTest.java      # Unit tests
└── AccountThreadSafetyTest.java     # Thread-safety and concurrency test
```

## 🧠 Strategy Pattern - How It’s Used

To separate the **business logic** of deposit and withdraw operations, I used the **Strategy Pattern**.

Instead of writing logic inside `AccountServiceImpl` directly, I defined this interface:

```java
public interface TransactionStrategy {
    void execute(Account account, int amount, LocalDate date);
}
```

And implemented it in two concrete classes:

- `DepositStrategy`: Validates deposit, updates balance, and records the transaction
- `WithdrawStrategy`: Validates funds, throws on insufficient balance, updates and records transaction

This makes it easy to:
- Add new transaction types (like `TransferStrategy`, `InterestStrategy`)
- Test each strategy independently
- Respect the **Open/Closed Principle** (OCP from SOLID)

## 🔐 Thread Safety

The project handles concurrent access by:
- Using `AtomicInteger` for thread-safe balance updates
- Using `CopyOnWriteArrayList` to store transactions safely across threads
- Using `synchronized` blocks around compound operations (like check-then-withdraw)

This ensures:
- No race conditions
- No inconsistent balances
- No lost transactions under multi-threading

## 🧪 Testing

I wrote a solid test suite using **JUnit 5**:

### ✅ Unit Tests
- `testDepositIncreasesBalance()`
- `testWithdrawDecreasesBalance()`
- `testWithdrawThrowsWhenInsufficientFunds()`

### ✅ Thread Safety Tests
I simulate 100 concurrent threads (50 deposits, 50 withdrawals) and verify:
- The final balance is correct
- The number of transactions is as expected
- No exceptions or inconsistencies occur

Run the tests with:

```bash
./mvnw test
```

## 🧾 Example Output

🧾 Final Bank Statement:
```
    DATE    |   AMOUNT  |  BALANCE
2025-06-28  |  -200     |  2600
2025-06-28  |  -500     |  2800
2025-06-28  |  -300     |  3300
2025-06-28  |   700     |  3600
2025-06-28  |   900     |  2900
2025-06-28  |   800     |  2000
2025-06-28  |   400     |  1200
2025-06-28  |   800     |  800
```

## 📍 Author

Built by **Youssef** as part of a technical challenge at SkyPay.