package com.us.mortgage.creditscoreapi;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

interface LoanApiClient {
	Flux<BorrowerWithLoans> findBorrowersWithLoans();
	Mono<BorrowerWithLoans> findBorrowerWithLoans(Long borrowerId);
}
