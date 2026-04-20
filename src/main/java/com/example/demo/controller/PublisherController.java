package com.example.demo.controller;

import com.example.demo.model.Publisher;
import com.example.demo.service.PublisherService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publishers")
public class PublisherController {

    private final PublisherService service;

    public PublisherController(PublisherService service) {
        this.service = service;
    }

    // GET ALL
    @GetMapping
    public List<Publisher> getAll() {
        return service.getAll();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public Publisher getById(@PathVariable Long id) {
        return service.getById(id);
    }

    // CREATE
    @PostMapping
    public Publisher create(@RequestBody Publisher p) {
        return service.create(p);
    }

    // UPDATE
    @PutMapping("/{id}")
    public Publisher update(@PathVariable Long id, @RequestBody Publisher p) {
        return service.update(id, p);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}