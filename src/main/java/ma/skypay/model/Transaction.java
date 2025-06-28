package ma.skypay.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Transaction {

    @NotNull
    @PastOrPresent(message = "Transaction date can't be in the future")
    private LocalDateTime date;

    @Positive(message = "Amount must be positive")
    private int amount;

    private int balanceAfterTransaction;
}