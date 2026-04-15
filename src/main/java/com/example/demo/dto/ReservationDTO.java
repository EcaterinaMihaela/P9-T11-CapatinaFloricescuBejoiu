package com.example.demo.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReservationDTO {
    private Long reservationId;
    private LocalDate reservationDate;
    private String status;
    private Long memberId;
    private Long bookId;
}