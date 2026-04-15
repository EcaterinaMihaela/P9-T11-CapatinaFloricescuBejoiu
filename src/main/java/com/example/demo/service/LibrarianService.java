package com.example.demo.service;
import com.example.demo.model.Librarian;
import java.util.List;

public interface LibrarianService {
    List<Librarian> getAll();
    Librarian getById(Long id);
    Librarian create(Librarian l);
    Librarian update(Long id, Librarian l);
    void delete(Long id);
}
