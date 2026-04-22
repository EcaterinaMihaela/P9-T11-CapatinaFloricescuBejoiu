package com.example.demo.service.impl;

import com.example.demo.dto.SecurityLogDTO;
import com.example.demo.model.SecurityLog;
import com.example.demo.model.User;
import com.example.demo.repository.RepositoryWrapper;
import com.example.demo.service.SecurityLogService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecurityLogServiceImpl implements SecurityLogService {

    private final RepositoryWrapper repo;

    public SecurityLogServiceImpl(RepositoryWrapper repo) {
        this.repo = repo;
    }

    @Override
    public List<SecurityLog> getAll() {
        return repo.securityLog.findAll();
    }

    @Override
    public SecurityLog getById(Long id) {
        return repo.securityLog.findById(id).orElse(null);
    }

    @Override
    public SecurityLog create(SecurityLogDTO dto) {

        User user = repo.user.findById(dto.getUserID()).orElse(null);

        SecurityLog log = new SecurityLog();
        log.setActionType(dto.getActionType());
        log.setLogDate(dto.getLogDate());
        log.setLogTime(dto.getLogTime());
        log.setAffectedTable(dto.getAffectedTable());
        log.setUser(user);

        return repo.securityLog.save(log);
    }

    @Override
    public SecurityLog update(Long id, SecurityLogDTO dto) {

        return repo.securityLog.findById(id).map(log -> {

            User user = repo.user.findById(dto.getUserID()).orElse(null);

            log.setActionType(dto.getActionType());
            log.setLogDate(dto.getLogDate());
            log.setLogTime(dto.getLogTime());
            log.setAffectedTable(dto.getAffectedTable());
            log.setUser(user);

            return repo.securityLog.save(log);

        }).orElse(null);
    }

    @Override
    public void delete(Long id) {
        repo.securityLog.deleteById(id);
    }
}