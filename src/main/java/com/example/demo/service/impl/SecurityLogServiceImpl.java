package com.example.demo.service.impl;
import com.example.demo.model.SecurityLog;
import com.example.demo.repository.SecurityLogRepository;
import com.example.demo.service.SecurityLogService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecurityLogServiceImpl implements SecurityLogService {

    private final SecurityLogRepository repo;

    public SecurityLogServiceImpl(SecurityLogRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<SecurityLog> getAll() {
        return repo.findAll();
    }

    @Override
    public SecurityLog getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public SecurityLog create(SecurityLog s) {
        return repo.save(s);
    }

    @Override
    public SecurityLog update(Long id, SecurityLog s) {
        return repo.findById(id).map(log -> {

            log.setActionType(s.getActionType());
            log.setLogDate(s.getLogDate());
            log.setLogTime(s.getLogTime());
            log.setAffectedTable(s.getAffectedTable());
            log.setUser(s.getUser());

            return repo.save(log);

        }).orElse(null);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
