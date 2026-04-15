package com.example.demo.dto;
import lombok.Data;

@Data
public class LoanDTO {
    private Long loanId;
    private String borrowDate;
    private String dueDate;
    private String returnDate;
    private String status;
}
