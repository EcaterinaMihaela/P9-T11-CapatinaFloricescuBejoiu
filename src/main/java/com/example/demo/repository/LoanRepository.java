package com.example.demo.repository;

import com.example.demo.model.Loan;

import java.util.List;

public interface LoanRepository extends BaseRepository<Loan, Long> {
    List<Loan> findByMember_User_Username(String username);
}
