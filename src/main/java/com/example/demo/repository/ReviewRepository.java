package com.example.demo.repository;

import com.example.demo.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.Review;

public interface ReviewRepository extends BaseRepository<Review, Long> {
}
