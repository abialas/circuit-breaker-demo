package com.us.mortgage.loan;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

/**
 * Created by adam.
 */
interface BorrowerRepository extends ReactiveCrudRepository<Borrower, Long> {

    Mono<Borrower> findByFirstNameAndLastName(String firstName, String lastName);

}
