package com.us.mortgage.creditscoreapi;

import lombok.Value;

/**
 * Created by adam.
 */
@Value
class Borrower {
    private Long id;
    private String firstName;
    private String lastName;
}
