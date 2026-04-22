package com.example.demo.controller;

import com.example.demo.dto.PublisherDTO;
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

    @GetMapping
    public List<Publisher> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Publisher getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public Publisher create(@RequestBody PublisherDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public Publisher update(@PathVariable Long id,
                            @RequestBody PublisherDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}