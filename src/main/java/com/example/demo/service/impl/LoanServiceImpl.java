package com.example.demo.service.impl;

import com.example.demo.model.Loan;
import com.example.demo.repository.LoanRepository;
import com.example.demo.service.LoanService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {

    private final LoanRepository repo;

    public LoanServiceImpl(LoanRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Loan> getAll() {
        return repo.findAll();
    }

    @Override
    public Loan getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Loan create(Loan l) {
        return repo.save(l);
    }

    @Override
    public Loan update(Long id, Loan l) {
        return repo.findById(id).map(loan -> {

            loan.setBorrowDate(l.getBorrowDate());
            loan.setDueDate(l.getDueDate());
            loan.setReturnDate(l.getReturnDate());
            loan.setStatus(l.getStatus());

            loan.setMember(l.getMember());
            loan.setLibrarian(l.getLibrarian());
            loan.setBook(l.getBook());

            return repo.save(loan);

        }).orElse(null);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}