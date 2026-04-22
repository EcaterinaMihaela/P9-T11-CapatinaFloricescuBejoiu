package com.example.demo.service;

import com.example.demo.dto.LibrarianDTO;
import com.example.demo.model.Librarian;

import java.util.List;

public interface LibrarianService {

    List<Librarian> getAll();

    Librarian getById(Long id);

    Librarian create(LibrarianDTO dto);

    Librarian update(Long id, LibrarianDTO dto);

    void delete(Long id);
}