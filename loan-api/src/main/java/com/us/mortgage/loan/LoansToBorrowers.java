package com.us.mortgage.loan;

import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Created by adam.
 */
@Value
@Table("loans_to_borrowers")
class LoansToBorrowers {
    @Id
    private Long id;
    private Long loanId;
    private Long borrowerId;

    static LoansToBorrowers of(Long loanId, Long borrowerId) {
        return new LoansToBorrowers(null, loanId, borrowerId);
    }

    interface LoanIdOnly {
        Long getLoanId();
    }
}
