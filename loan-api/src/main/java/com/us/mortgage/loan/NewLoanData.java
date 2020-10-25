package com.us.mortgage.loan;

import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by adam.
 */
@Value
public class NewLoanData {
    private String borrowerFirstName;
    private String borrowerLastName;
    private LocalDate loanSignedDate;
    private LocalDate loanEndDate;
    private BigDecimal loanAmount;
}
