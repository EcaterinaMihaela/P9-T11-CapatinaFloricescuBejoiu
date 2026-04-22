package com.example.demo.service;

import com.example.demo.dto.LoanDTO;
import com.example.demo.model.Loan;

import java.util.List;

public interface LoanService {

    List<Loan> getAll();

    Loan getById(Long id);

    Loan create(LoanDTO dto);

    Loan update(Long id, LoanDTO dto);

    void delete(Long id);
}