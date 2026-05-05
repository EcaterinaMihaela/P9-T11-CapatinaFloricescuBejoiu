package com.example.demo.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "member")
public class Member {

    @Id
    @Column(name = "memberid")
    private Long memberID;

    @Column(name = "borrow_limit")
    private int borrowLimit = 5;

    private String status = "ACTIVE";

    @Column(name = "ban_reason")
    private String banReason;

    @OneToOne
    @JoinColumn(name = "member_id")
    private User user;
}