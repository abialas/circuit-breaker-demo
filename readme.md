# About
This project demonstrates the circuit breaker pattern implemented with Spring Cloud Circuit Breaker with Resilience4J implementation.

## loan-api
Dummy service which provides data about borrowers and their loans (initialized with dummy data).

## creditscore-api
Dummy service which calls loan-api service and for each borrower calculates credit score. Call to loan-api by default is done via a circuit breaker. However, this behaviour can be disabled and service can be called directly just by setting property value in application.properties file:
```
circuit.breaker.enabled=false
```

## Presentation
Short presentation about how circuit breaker works and how to configure / use it is located at https://abialas.github.io/presentations/circuit-breaker.


