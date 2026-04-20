package com.example.demo.controller;

import com.example.demo.model.Loan;
import com.example.demo.service.LoanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/loans")
public class LoanController {

    private final LoanService service;

    public LoanController(LoanService service) {
        this.service = service;
    }

    @GetMapping
    public List<Loan> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Loan getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public Loan create(@RequestBody Loan loan) {
        return service.create(loan);
    }

    @PutMapping("/{id}")
    public Loan update(@PathVariable Long id, @RequestBody Loan loan) {
        return service.update(id, loan);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}