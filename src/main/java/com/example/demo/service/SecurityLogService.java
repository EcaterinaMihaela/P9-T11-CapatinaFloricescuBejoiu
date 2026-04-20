package com.example.demo.service;
import com.example.demo.model.SecurityLog;

import java.util.List;

public interface SecurityLogService {

    List<SecurityLog> getAll();

    SecurityLog getById(Long id);

    SecurityLog create(SecurityLog s);

    SecurityLog update(Long id, SecurityLog s);

    void delete(Long id);
}

