package com.example.demo.service.impl;

import com.example.demo.dto.LoanDTO;
import com.example.demo.model.*;
import com.example.demo.repository.RepositoryWrapper;
import com.example.demo.service.LoanService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {

    private final RepositoryWrapper repo;

    public LoanServiceImpl(RepositoryWrapper repo) {
        this.repo = repo;
    }

    @Override
    public List<Loan> getAll() {
        return repo.loan.findAllSafe();
    }

    @Override
    public Loan getById(Long id) {
        return repo.loan.findByIdSafe(id).orElse(null);
    }

    @Override
    public Loan create(LoanDTO dto) {

        Loan loan = new Loan();

        loan.setBorrowDate(LocalDate.parse(dto.getBorrowDate()));
        loan.setDueDate(LocalDate.parse(dto.getDueDate()));

        if (dto.getReturnDate() != null) {
            loan.setReturnDate(LocalDate.parse(dto.getReturnDate()));
        }

        loan.setStatus(dto.getStatus());

        Member member = repo.member.findByIdSafe(dto.getMemberId()).orElse(null);
        Librarian librarian = repo.librarian.findByIdSafe(dto.getLibrarianId()).orElse(null);
        Book book = repo.book.findByIdSafe(dto.getBookId()).orElse(null);

        loan.setMember(member);
        loan.setLibrarian(librarian);
        loan.setBook(book);

        return repo.loan.saveSafe(loan);
    }

    @Override
    public Loan update(Long id, LoanDTO dto) {

        return repo.loan.findByIdSafe(id).map(loan -> {

            loan.setBorrowDate(LocalDate.parse(dto.getBorrowDate()));
            loan.setDueDate(LocalDate.parse(dto.getDueDate()));

            if (dto.getReturnDate() != null) {
                loan.setReturnDate(LocalDate.parse(dto.getReturnDate()));
            }

            loan.setStatus(dto.getStatus());

            Member member = repo.member.findByIdSafe(dto.getMemberId()).orElse(null);
            Librarian librarian = repo.librarian.findByIdSafe(dto.getLibrarianId()).orElse(null);
            Book book = repo.book.findByIdSafe(dto.getBookId()).orElse(null);

            loan.setMember(member);
            loan.setLibrarian(librarian);
            loan.setBook(book);

            return repo.loan.saveSafe(loan);

        }).orElse(null);
    }

    @Override
    public void delete(Long id) {
        repo.loan.deleteSafe(id);
    }
}