package com.example.demo.service;

import com.example.demo.model.Loan;

import java.util.List;

public interface LoanService {

    List<Loan> getAll();

    Loan getById(Long id);

    Loan create(Loan loan);

    Loan update(Long id, Loan loan);

    void delete(Long id);
}