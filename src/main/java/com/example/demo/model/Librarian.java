package com.example.demo.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Getter
@Setter
@Entity
@Table(name = "librarian")
public class Librarian {

    @Id
    @Column(name = "librarianid")
    private Long librarianID;

    private String responsibilities;

    @OneToOne
    @MapsId
    @JoinColumn(name = "librarianid")  // ← FĂRĂ @MapsId!
    @JsonBackReference
    private User user;
}