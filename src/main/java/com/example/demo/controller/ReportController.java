package com.example.demo.controller;

import com.example.demo.model.Report;
import com.example.demo.service.ReportService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {

    private final ReportService service;

    public ReportController(ReportService service) {
        this.service = service;
    }

    @GetMapping
    public List<Report> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Report getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public Report create(@RequestBody Report report) {
        return service.create(report);
    }

    @PutMapping("/{id}")
    public Report update(@PathVariable Long id, @RequestBody Report report) {
        return service.update(id, report);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}