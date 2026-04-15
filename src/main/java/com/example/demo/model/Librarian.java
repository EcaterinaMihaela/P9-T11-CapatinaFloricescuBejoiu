package com.example.demo.model;
import jakarta.persistence.*;

import lombok.Data;

@Data
@Entity
public class Librarian {

    @Id
    private Long librarianID;

    private String responsibilities;

    @OneToOne
    @MapsId
    @JoinColumn(name = "librarianID")
    private User user;
}