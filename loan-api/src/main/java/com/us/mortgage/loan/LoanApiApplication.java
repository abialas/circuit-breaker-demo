package com.us.mortgage.loan;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.connectionfactory.init.ConnectionFactoryInitializer;
import org.springframework.data.r2dbc.connectionfactory.init.ResourceDatabasePopulator;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootApplication
@EnableR2dbcRepositories
public class LoanApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoanApiApplication.class, args);
    }

    @Bean
    ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));
        return initializer;
    }

    @Bean
    CommandLineRunner initSampleData(LoanFacade loanFacade) {
        return args -> {
            loanFacade.addNewLoanForBorrower(
                    new NewLoanData("Donald", "Duck",
                            LocalDate.now(),
                            LocalDate.now().plusYears(30),
                            new BigDecimal("400000"))
            ).blockLast();

            loanFacade.addNewLoanForBorrower(
                    new NewLoanData("Jack", "Dummy",
                            LocalDate.now().minusYears(1).minusMonths(1),
                            LocalDate.now().minusYears(1).minusMonths(1).plusYears(30),
                            new BigDecimal("1000000"))
            ).blockLast();
        };
    }

}
