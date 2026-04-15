package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.Publisher;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {
}
