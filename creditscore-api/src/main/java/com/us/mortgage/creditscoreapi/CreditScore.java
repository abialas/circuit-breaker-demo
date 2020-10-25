package com.us.mortgage.creditscoreapi;

import lombok.Value;

import java.math.BigDecimal;

/**
 * Created by adam.
 */
@Value
public class CreditScore {
    private Borrower borrower;
    private BigDecimal score;
}
