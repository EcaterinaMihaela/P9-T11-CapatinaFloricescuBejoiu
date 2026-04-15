package com.example.demo.model;
import jakarta.persistence.*;

import lombok.Data;

@Data
@Entity
public class Member {

    @Id
    private Long memberID;

    private int borrowLimit = 5;
    private String status = "ACTIVE";
    private String banReason;

    @OneToOne
    @MapsId
    @JoinColumn(name = "memberID")
    private User user;
}