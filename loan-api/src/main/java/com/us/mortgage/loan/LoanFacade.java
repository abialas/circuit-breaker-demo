package com.us.mortgage.loan;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Created by adam.
 */
@Component
public class LoanFacade {
    private final BorrowerRepository borrowerRepository;
    private final LoanRepository loanRepository;
    private final LoansToBorrowersRepository loansToBorrowersRepository;

    public LoanFacade(BorrowerRepository borrowerRepository,
                      LoanRepository loanRepository,
                      LoansToBorrowersRepository loansToBorrowersRepository) {
        this.borrowerRepository = borrowerRepository;
        this.loanRepository = loanRepository;
        this.loansToBorrowersRepository = loansToBorrowersRepository;
    }

    public Flux<BorrowerWithLoans> addNewLoanForBorrower(NewLoanData newLoanData) {
        return Mono.just(newLoanData)
                .flatMap(loanData -> Mono.just(Loan.of(loanData.getLoanSignedDate(), loanData.getLoanEndDate(),
                        loanData.getLoanAmount()))
                        .flatMap(loanRepository::save))
                .zipWith(borrowerRepository.findByFirstNameAndLastName(newLoanData.getBorrowerFirstName(),
                        newLoanData.getBorrowerLastName())
                        .switchIfEmpty(Mono.just(Borrower.of(newLoanData.getBorrowerFirstName(),
                                newLoanData.getBorrowerLastName()))
                                .flatMap(borrowerRepository::save)))
                .flatMap(loanWithBorrower -> Mono.just(LoansToBorrowers.of(loanWithBorrower.getT1().getId(),
                        loanWithBorrower.getT2().getId()))
                        .flatMap(loansToBorrowersRepository::save))
                .map(LoansToBorrowers::getBorrowerId)
                .flatMapMany(this::findBorrowerWithLoans);
    }

    public Flux<BorrowerWithLoans> findBorrowersWithLoans() {
        return borrowerRepository.findAll()
                .flatMap(borrower -> findLoansForBorrower(borrower)
                        .map(loans -> new BorrowerWithLoans(borrower, loans))
                );
    }

    public Mono<BorrowerWithLoans> findBorrowerWithLoans(Long borrowerId) {
        return borrowerRepository.findById(borrowerId)
                .flatMap(borrower -> findLoansForBorrower(borrower)
                        .map(loans -> new BorrowerWithLoans(borrower, loans))
                );
    }

    private Mono<List<Loan>> findLoansForBorrower(Borrower borrower) {
        return loansToBorrowersRepository.findAllByBorrowerId(borrower.getId())
                .map(LoansToBorrowers.LoanIdOnly::getLoanId)
                .flatMap(loanRepository::findById)
                .collectList();
    }

}
