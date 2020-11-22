package com.us.mortgage.creditscoreapi;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

    public Mono<CreditScore> findCreditScore(Long borrowerId) {
        return loanApiClient.findBorrowerWithLoans(borrowerId)
                .map(BorrowerWithLoans::getBorrower)
                .map(borrower -> new CreditScore(borrower, BigDecimal.ONE));
    }

}
