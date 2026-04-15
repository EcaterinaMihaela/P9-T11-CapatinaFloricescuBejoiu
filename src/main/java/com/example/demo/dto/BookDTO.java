package com.example.demo.dto;
import lombok.Data;

@Data
public class BookDTO {
    private Long id;
    private String bookTitle;
    private String bookDescription;
    private String isbn;
    private int availableStock;
    private String status;
}
