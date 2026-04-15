package com.example.demo.dto;

import lombok.Data;

@Data
public class MemberDTO {
    private Long memberId;
    private int borrowLimit;
    private String status;
    private String banReason;
}