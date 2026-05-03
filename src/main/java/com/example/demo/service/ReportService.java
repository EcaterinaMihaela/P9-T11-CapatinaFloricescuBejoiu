package com.example.demo.service;

import com.example.demo.dto.ReportDTO;
import com.example.demo.model.Report;

import java.util.List;
import java.util.Map;

public interface ReportService {

    List<Report> getAll();

    Report getById(Long id);

    Report create(ReportDTO dto);

    Report update(Long id, ReportDTO dto);

    void delete(Long id);

    Map<String, Object> getDashboardData(String month);
}