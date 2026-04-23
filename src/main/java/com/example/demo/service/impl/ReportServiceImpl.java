package com.example.demo.service.impl;

import com.example.demo.dto.ReportDTO;
import com.example.demo.model.Report;
import com.example.demo.model.User;
import com.example.demo.repository.impl.RepositoryWrapper;
import com.example.demo.service.ReportService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    private final RepositoryWrapper repo;

    public ReportServiceImpl(RepositoryWrapper repo) {
        this.repo = repo;
    }

    @Override
    public List<Report> getAll() {
        return repo.report.findAll();
    }

    @Override
    public Report getById(Long id) {
        return repo.report.findById(id).orElse(null);
    }

    @Override
    public Report create(ReportDTO dto) {

        User user = repo.user.findById(dto.getUserID()).orElse(null);

        Report r = new Report();
        r.setType(dto.getType());
        r.setGenerationDate(dto.getGenerationDate());
        r.setGenerationTime(dto.getGenerationTime());
        r.setFormat(dto.getFormat());
        r.setUser(user);

        return repo.report.save(r);
    }

    @Override
    public Report update(Long id, ReportDTO dto) {

        return repo.report.findById(id).map(r -> {

            r.setType(dto.getType());
            r.setGenerationDate(dto.getGenerationDate());
            r.setGenerationTime(dto.getGenerationTime());
            r.setFormat(dto.getFormat());

            User user = repo.user.findById(dto.getUserID()).orElse(null);
            r.setUser(user);

            return repo.report.save(r);

        }).orElse(null);
    }

    @Override
    public void delete(Long id) {
        repo.report.deleteById(id);
    }
}