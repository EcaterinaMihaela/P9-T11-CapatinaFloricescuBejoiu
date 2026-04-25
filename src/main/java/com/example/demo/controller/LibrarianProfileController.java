package com.example.demo.controller;

import com.example.demo.dto.LibrarianDTO;
import com.example.demo.model.Librarian;
import com.example.demo.service.LibrarianService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/librarians")
public class LibrarianProfileController {

    private final LibrarianService service;

    public LibrarianProfileController(LibrarianService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public Librarian getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public Librarian update(@PathVariable Long id, @RequestBody LibrarianDTO dto) {
        return service.update(id, dto);
    }
}