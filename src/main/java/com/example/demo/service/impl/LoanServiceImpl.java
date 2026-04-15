package com.example.demo.service.impl;

import com.example.demo.model.Loan;
import com.example.demo.repository.LoanRepository;
import com.example.demo.service.LoanService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {

    private final LoanRepository repository;

    public LoanServiceImpl(LoanRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Loan> getAll() {
        return repository.findAll();
    }

    @Override
    public Loan getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan not found with id: " + id));
    }

    @Override
    public Loan create(Loan loan) {
        return repository.save(loan);
    }

    @Override
    public Loan update(Long id, Loan loan) {
        Loan existing = getById(id);

        existing.setBorrowDate(loan.getBorrowDate());
        existing.setDueDate(loan.getDueDate());
        existing.setReturnDate(loan.getReturnDate());
        existing.setStatus(loan.getStatus());
        existing.setMember(loan.getMember());
        existing.setLibrarian(loan.getLibrarian());
        existing.setBook(loan.getBook());

        return repository.save(existing);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}