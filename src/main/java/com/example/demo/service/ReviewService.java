package com.example.demo.service;

import com.example.demo.model.Review;
import java.util.List;

public interface ReviewService {

    List<Review> getAll();

    Review getById(Long id);

    Review create(Review review);

    Review update(Long id, Review review);

    void delete(Long id);
}