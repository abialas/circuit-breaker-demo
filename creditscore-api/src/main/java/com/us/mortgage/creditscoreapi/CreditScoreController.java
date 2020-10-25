package com.us.mortgage.creditscoreapi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * Created by adam.
 */
@RestController
@RequestMapping("scores")
class CreditScoreController {
    private final CreditScoreFacade creditScoreFacade;

    CreditScoreController(CreditScoreFacade creditScoreFacade) {
        this.creditScoreFacade = creditScoreFacade;
    }

    @GetMapping
    Flux<CreditScore> creditScores() {
        return creditScoreFacade.findCreditScores();
    }

}
