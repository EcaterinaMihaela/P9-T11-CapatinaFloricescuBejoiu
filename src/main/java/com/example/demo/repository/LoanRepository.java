package com.example.demo.repository;

import com.example.demo.model.Loan;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.util.List;

import java.util.List;

public interface LoanRepository extends BaseRepository<Loan, Long> {
    List<Loan> findByMember_User_Username(String username);
    List<Loan> findAllByReturnDateIsNull();


    // Găsește împrumuturile care au depășit data de returnare și nu au fost returnate
    @Query("SELECT l FROM Loan l WHERE l.dueDate < CURRENT_DATE AND l.returnDate IS NULL")
    List<Loan> findOverdueLoans();

}