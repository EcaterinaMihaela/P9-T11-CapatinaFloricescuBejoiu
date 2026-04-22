package com.example.demo.service;

import com.example.demo.dto.SecurityLogDTO;
import com.example.demo.model.SecurityLog;

import java.util.List;

public interface SecurityLogService {

    List<SecurityLog> getAll();

    SecurityLog getById(Long id);

    SecurityLog create(SecurityLogDTO dto);

    SecurityLog update(Long id, SecurityLogDTO dto);

    void delete(Long id);
}