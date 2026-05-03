package com.example.demo.controller;

import com.example.demo.dto.LoanDTO;
import com.example.demo.model.Loan;
import com.example.demo.repository.LoanRepository;
import com.example.demo.service.LoanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/loans")
public class LoanController {

    private final LoanService service;
    private LoanRepository loanRepository;

    public LoanController(LoanService service, LoanRepository loanRepository) {
        this.service = service;
        this.loanRepository = loanRepository;
    }

    @GetMapping
    public ResponseEntity<List<Loan>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Loan> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }
    @GetMapping("/my-loans")
    public List<Loan> getMyLoans(@RequestParam("username") String username) {
        return loanRepository.findByMember_User_Username(username);
    }

    @PostMapping
    public ResponseEntity<Loan> create(@RequestBody LoanDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Loan> update(@PathVariable Long id,
                                       @RequestBody LoanDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/return")
    public ResponseEntity<Loan> returnBook(@PathVariable Long id) {
        return ResponseEntity.ok(service.returnBook(id));
    }
}