package com.us.mortgage.loan;

import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by adam.
 */
@Value
@Table("loan")
class Loan {
    @Id
    private Long id;
    private LocalDate signedDate;
    private LocalDate endDate;
    private BigDecimal amount;

    static Loan of(LocalDate signedDate, LocalDate endDate, BigDecimal amount) {
        return new Loan(null, signedDate, endDate, amount);
    }
}
