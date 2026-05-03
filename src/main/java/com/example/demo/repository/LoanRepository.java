package com.example.demo.repository;

import com.example.demo.model.Loan;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.util.List;

import java.util.List;

public interface LoanRepository extends BaseRepository<Loan, Long> {
    List<Loan> findByMember_User_Username(String username);


    // Calculează numărul total de împrumuturi pe o lună anume
    long countByBorrowDateStartingWith(String month); // ex: "2026-05"

    // Găsește cele mai împrumutate cărți
    @Query("SELECT l.book, COUNT(l) as count FROM Loan l GROUP BY l.book ORDER BY count DESC")
    List<Object[]> findTopBorrowedBooks(Pageable pageable);

    // Găsește împrumuturile care au depășit data de returnare și nu au fost returnate
    @Query("SELECT l FROM Loan l WHERE l.dueDate < CURRENT_DATE AND l.returnDate IS NULL")
    List<Loan> findOverdueLoans();
}