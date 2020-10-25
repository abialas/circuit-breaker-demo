package com.us.mortgage.creditscoreapi;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;

/**
 * Created by adam.
 */
@Component
public class CreditScoreFacade {
    private final LoanApiClient loanApiClient;

    public CreditScoreFacade(LoanApiClient loanApiClient) {
        this.loanApiClient = loanApiClient;
    }

    public Flux<CreditScore> findCreditScores() {
        return loanApiClient.findBorrowersWithLoans()
                .map(BorrowerWithLoans::getBorrower)
                .map(borrower -> new CreditScore(borrower, BigDecimal.ONE));
    }

}
