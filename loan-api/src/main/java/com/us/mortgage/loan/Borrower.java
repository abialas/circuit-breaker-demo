package com.us.mortgage.loan;

import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Created by adam.
 */
@Value
@Table("borrower")
class Borrower {
    @Id
    private Long id;
    private String firstName;
    private String lastName;

    static Borrower of(String firstName, String lastName) {
        return new Borrower(null, firstName, lastName);
    }
}
