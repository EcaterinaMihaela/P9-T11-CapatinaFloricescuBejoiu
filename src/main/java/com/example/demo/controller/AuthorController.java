package com.example.demo.controller;

import com.example.demo.dto.AuthorDTO;
import com.example.demo.model.Author;
import com.example.demo.service.AuthorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService service;

    public AuthorController(AuthorService service) {
        this.service = service;
    }

    @GetMapping
    public List<Author> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Author getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public Author create(@RequestBody AuthorDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public Author update(@PathVariable Long id, @RequestBody AuthorDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}