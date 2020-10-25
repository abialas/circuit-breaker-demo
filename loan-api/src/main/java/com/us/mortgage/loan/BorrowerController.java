package com.us.mortgage.loan;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by adam.
 */
@RestController
@RequestMapping("borrowers")
class BorrowerController {
    private final LoanFacade loanFacade;

    BorrowerController(LoanFacade loanFacade) {
        this.loanFacade = loanFacade;
    }

    @GetMapping
    Flux<BorrowerWithLoans> findBorrowersWithLoans() {
        return loanFacade.findBorrowersWithLoans();
    }

    @GetMapping("/{borrowerId}")
    Mono<BorrowerWithLoans> findBorrowerWithLoans(@PathVariable Long borrowerId) {
        return loanFacade.findBorrowerWithLoans(borrowerId);
    }

}
