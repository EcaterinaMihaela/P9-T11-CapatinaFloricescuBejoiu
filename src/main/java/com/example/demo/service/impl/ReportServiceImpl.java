package com.example.demo.service.impl;

import com.example.demo.model.Report;
import com.example.demo.repository.ReportRepository;
import com.example.demo.service.ReportService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepository repo;

    public ReportServiceImpl(ReportRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Report> getAll() {
        return repo.findAll();
    }

    @Override
    public Report getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Report create(Report report) {
        return repo.save(report);
    }

    @Override
    public Report update(Long id, Report r) {
        return repo.findById(id).map(rep -> {

            rep.setType(r.getType());
            rep.setGenerationDate(r.getGenerationDate());
            rep.setGenerationTime(r.getGenerationTime());
            rep.setFormat(r.getFormat());
            rep.setUser(r.getUser());

            return repo.save(rep);

        }).orElse(null);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}