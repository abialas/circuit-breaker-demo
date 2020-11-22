package com.us.mortgage.creditscoreapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * Created by adam.
 */
@Component
@ConditionalOnProperty(value = "circuit.breaker.enabled", havingValue = "false")
final class LoanApiClientWithoutCircuitBreaker implements LoanApiClient {
    private final WebClient webClient;

    LoanApiClientWithoutCircuitBreaker(@Value("${service.loan-api.address}") String loanApiAddress) {
        this.webClient = WebClient.builder().baseUrl(loanApiAddress).build();
    }

    public Flux<BorrowerWithLoans> findBorrowersWithLoans() {
        return webClient
                .get()
                .uri("/borrowers")
                .retrieve()
                .bodyToFlux(BorrowerWithLoans.class)
                .timeout(Duration.ofSeconds(3))
                .log()
                .onErrorResume(throwable -> Flux.empty());
    }

    public Mono<BorrowerWithLoans> findBorrowerWithLoans(Long borrowerId) {
        return webClient
                .get()
                .uri("/borrowers/{id}", borrowerId)
                .retrieve()
                .bodyToMono(BorrowerWithLoans.class)
                .timeout(Duration.ofSeconds(3))
                .log()
                .onErrorResume(throwable -> Mono.empty());
    }

}
