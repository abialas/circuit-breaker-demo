package com.us.mortgage.loan;

import lombok.Value;
import java.util.List;

/**
 * Created by adam.
 */
@Value
public class BorrowerWithLoans {
    private Borrower borrower;
    private List<Loan> loans;
}
