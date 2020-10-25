package com.us.mortgage.creditscoreapi;

import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by adam.
 */
@Value
class BorrowerWithLoans {
    private Borrower borrower;
    private List<Loan> loans;

    @Value
    static class Loan {
        private Long id;
        private LocalDate signedDate;
        private LocalDate endDate;
        private BigDecimal amount;
    }
}
