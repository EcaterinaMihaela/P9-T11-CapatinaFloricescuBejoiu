package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.Librarian;

public interface LibrarianRepository extends JpaRepository<Librarian, Long> {
}
