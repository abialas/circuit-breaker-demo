package com.us.mortgage.creditscoreapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by adam.
 */
@Component
@ConditionalOnProperty(value = "circuit.breaker.enabled", havingValue = "true", matchIfMissing = true)
final class LoanApiClientWithCircuitBreaker implements LoanApiClient {
	private final WebClient webClient;
	private final ReactiveCircuitBreaker circuitBreaker;

	LoanApiClientWithCircuitBreaker(ReactiveCircuitBreakerFactory circuitBreakerFactory,
									@Value("${service.loan-api.address}") String loanApiAddress) {
		this.webClient = WebClient.builder().baseUrl(loanApiAddress).build();
		this.circuitBreaker = circuitBreakerFactory.create("loanApi");
	}

	public Flux<BorrowerWithLoans> findBorrowersWithLoans() {
		// this one takes at least 4 sec
		return circuitBreaker.run(webClient
				.get()
				.uri("/borrowers")
				.retrieve()
				.bodyToFlux(BorrowerWithLoans.class)
				.log(), exception -> Flux.empty());
	}

	@Override
	public Mono<BorrowerWithLoans> findBorrowerWithLoans(Long borrowerId) {
		// this one is quick
		return circuitBreaker.run(webClient
				.get()
				.uri("/borrowers/{id}", borrowerId)
				.retrieve()
				.bodyToMono(BorrowerWithLoans.class)
				.log(), exception -> Mono.empty());
	}

}
