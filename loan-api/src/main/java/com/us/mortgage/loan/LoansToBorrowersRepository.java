package com.us.mortgage.loan;

import com.us.mortgage.loan.LoansToBorrowers.LoanIdOnly;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

/**
 * Created by adam.
 */
interface LoansToBorrowersRepository extends ReactiveCrudRepository<LoansToBorrowers, Long> {

    Flux<LoanIdOnly> findAllByBorrowerId(Long borrowerId);

}
