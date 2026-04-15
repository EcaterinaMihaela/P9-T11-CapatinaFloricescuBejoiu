package com.example.demo.dto;

import lombok.Data;

@Data
public class ReviewDTO {
    private Long reviewId;
    private int rating;
    private String reviewText;
    private Long memberId;
    private Long bookId;
}