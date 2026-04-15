package com.example.demo.service.impl;

import com.example.demo.model.Review;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.service.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository repo;

    public ReviewServiceImpl(ReviewRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Review> getAll() {
        return repo.findAll();
    }

    @Override
    public Review getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Review create(Review review) {
        return repo.save(review);
    }

    @Override
    public Review update(Long id, Review review) {
        return repo.findById(id).map(r -> {
            r.setRating(review.getRating());
            r.setReviewText(review.getReviewText());
            r.setMember(review.getMember());
            r.setBook(review.getBook());
            return repo.save(r);
        }).orElse(null);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}