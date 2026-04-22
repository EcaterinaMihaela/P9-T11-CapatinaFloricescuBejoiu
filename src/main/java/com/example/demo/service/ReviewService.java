package com.example.demo.service;

import com.example.demo.dto.ReviewDTO;
import com.example.demo.model.Review;

import java.util.List;

public interface ReviewService {

    List<Review> getAll();

    Review getById(Long id);

    Review create(ReviewDTO dto);

    Review update(Long id, ReviewDTO dto);

    void delete(Long id);
}