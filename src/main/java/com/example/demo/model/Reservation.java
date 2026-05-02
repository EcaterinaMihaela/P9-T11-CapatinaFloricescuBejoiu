package com.example.demo.model;
import jakarta.persistence.*;
import java.time.LocalDate;

import lombok.Data;

@Data
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationID;

    private LocalDate reservationDate;
    private String status = "PENDING";

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
}
