package com.example.demo.controller;

import com.example.demo.dto.ReviewDTO;
import com.example.demo.model.Review;
import com.example.demo.service.ReviewService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService service;

    public ReviewController(ReviewService service) {
        this.service = service;
    }

    @GetMapping
    public List<Review> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Review getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public Review create(@RequestBody ReviewDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public Review update(@PathVariable Long id, @RequestBody ReviewDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}