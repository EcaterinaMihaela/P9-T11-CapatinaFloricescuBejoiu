package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.SecurityLog;

public interface SecurityLogRepository extends JpaRepository<SecurityLog, Long> {
}
