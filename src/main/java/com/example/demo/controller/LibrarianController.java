package com.example.demo.controller;

import com.example.demo.dto.LibrarianDTO;
import com.example.demo.model.Librarian;
import com.example.demo.service.LibrarianService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/librarians")
public class LibrarianController {

    private final LibrarianService service;

    public LibrarianController(LibrarianService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Librarian>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Librarian> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<Librarian> create(@RequestBody LibrarianDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Librarian> update(@PathVariable Long id,
                                            @RequestBody LibrarianDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}