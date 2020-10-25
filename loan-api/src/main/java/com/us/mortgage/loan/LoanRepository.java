package com.us.mortgage.loan;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

/**
 * Created by adam.
 */
interface LoanRepository extends ReactiveCrudRepository<Loan, Long> {
}
