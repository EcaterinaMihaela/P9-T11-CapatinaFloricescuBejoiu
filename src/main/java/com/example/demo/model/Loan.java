package com.example.demo.model;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Data;

@Data
@Entity
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loanID;

    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;

    private String status = "BORROWED";

    @ManyToOne
    @JoinColumn(name = "memberID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "librarianID")
    private Librarian librarian;

    @ManyToOne
    @JoinColumn(name = "bookID")
    private Book book;
}
