package com.example.demo.controller;

import com.example.demo.dto.LoanDTO;
import com.example.demo.model.Loan;
import com.example.demo.service.LoanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/loans")
public class LoanController {

    private final LoanService service;

    public LoanController(LoanService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Loan>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Loan> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
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

    @PutMapping("/{id}/extend")
    public ResponseEntity<Loan> extendLoan(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String newDueDate = body.get("newDueDate");
        return ResponseEntity.ok(service.extendLoan(id, LocalDate.parse(newDueDate)));
    }
}