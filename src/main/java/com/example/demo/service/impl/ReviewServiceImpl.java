package com.example.demo.service.impl;

import com.example.demo.dto.ReviewDTO;
import com.example.demo.model.Book;
import com.example.demo.model.Member;
import com.example.demo.model.Review;
import com.example.demo.repository.impl.RepositoryWrapper;
import com.example.demo.service.ReviewService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final RepositoryWrapper repo;

    public ReviewServiceImpl(RepositoryWrapper repo) {
        this.repo = repo;
    }

    @Override
    public List<Review> getAll() {
        return repo.review.findAll();
    }

    @Override
    public Review getById(Long id) {
        return repo.review.findById(id).orElse(null);
    }

    @Override
    public Review create(ReviewDTO dto) {

        Member member = repo.member.findById(dto.getMemberId()).orElse(null);
        Book book = repo.book.findById(dto.getBookId()).orElse(null);

        Review review = new Review();
        review.setRating(dto.getRating());
        review.setReviewText(dto.getReviewText());
        review.setReviewDate(LocalDate.now());

        review.setMember(member);
        review.setBook(book);

        return repo.review.save(review);
    }

    @Override
    public Review update(Long id, ReviewDTO dto) {

        return repo.review.findById(id).map(r -> {

            Member member = repo.member.findById(dto.getMemberId()).orElse(null);
            Book book = repo.book.findById(dto.getBookId()).orElse(null);

            r.setRating(dto.getRating());
            r.setReviewText(dto.getReviewText());
            r.setMember(member);
            r.setBook(book);

            return repo.review.save(r);

        }).orElse(null);
    }

    @Override
    public void delete(Long id) {
        repo.review.deleteById(id);
    }
}