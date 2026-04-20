package com.example.demo.service;

import com.example.demo.model.Report;

import java.util.List;

public interface ReportService {

    List<Report> getAll();

    Report getById(Long id);

    Report create(Report report);

    Report update(Long id, Report report);

    void delete(Long id);
}