package com.us.mortgage.creditscoreapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

/**
 * Created by adam.
 */
@Component
class LoanApiClient {
    private final WebClient webClient;
    private final ReactiveCircuitBreaker circuitBreaker;

    LoanApiClient(ReactiveCircuitBreakerFactory circuitBreakerFactory,
                  @Value("${service.loan-api.address}") String loanApiAddress) {
        this.webClient = WebClient.builder().baseUrl(loanApiAddress).build();
        this.circuitBreaker = circuitBreakerFactory.create("loanApiCircuitBreaker");
    }

    Flux<BorrowerWithLoans> findBorrowersWithLoans() {
        return circuitBreaker.run(webClient
                .get()
                .uri("/borrowers")
                .retrieve()
                .bodyToFlux(BorrowerWithLoans.class)
                .log(), exception -> Flux.empty());
    }

}
