package com.example.demo.service.impl;

import com.example.demo.dto.LoanDTO;
import com.example.demo.model.*;
import com.example.demo.repository.impl.RepositoryWrapper;
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

        loan.setReturnDate(null);
        loan.setStatus("BORROWED");

        Member member = repo.member.findByIdSafe(dto.getMemberId())
                .orElseThrow(() -> new RuntimeException("Member not found"));

        Librarian librarian = repo.librarian.findByIdSafe(dto.getLibrarianId())
                .orElseThrow(() -> new RuntimeException("Librarian not found"));

        Book book = repo.book.findByIdSafe(dto.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        //  VALIDARE STOCK
        if (book.getAvailableStock() <= 0) {
            throw new RuntimeException("Book not available");
        }

        //  LIMITĂ BORROW
        long activeLoans = repo.loan.findAllSafe().stream()
                .filter(l -> l.getMember().getMemberID().equals(member.getMemberID()))
                .filter(l -> l.getReturnDate() == null)
                .count();

        if (activeLoans >= 3) {
            throw new RuntimeException("Borrowing limit reached");
        }

        loan.setMember(member);
        loan.setLibrarian(librarian);
        loan.setBook(book);

        // scade stock
        book.setAvailableStock(book.getAvailableStock() - 1);
        repo.book.saveSafe(book);

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
    @Override
    public Loan returnBook(Long loanId) {

        Loan loan = repo.loan.findByIdSafe(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        if (loan.getReturnDate() != null) {
            throw new RuntimeException("Book already returned");
        }

        // 1. setezi return date
        loan.setReturnDate(LocalDate.now());

        // 2. status
        loan.setStatus("RETURNED");

        // 3. returnezi stock la book
        Book book = loan.getBook();
        book.setAvailableStock(book.getAvailableStock() + 1);
        repo.book.saveSafe(book);

        return repo.loan.saveSafe(loan);
    }
}