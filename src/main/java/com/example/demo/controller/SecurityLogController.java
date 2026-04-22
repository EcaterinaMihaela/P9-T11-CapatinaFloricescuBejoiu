package com.example.demo.controller;

import com.example.demo.dto.SecurityLogDTO;
import com.example.demo.model.SecurityLog;
import com.example.demo.service.SecurityLogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/security-logs")
public class SecurityLogController {

    private final SecurityLogService service;

    public SecurityLogController(SecurityLogService service) {
        this.service = service;
    }

    @GetMapping
    public List<SecurityLog> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public SecurityLog getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public SecurityLog create(@RequestBody SecurityLogDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public SecurityLog update(@PathVariable Long id, @RequestBody SecurityLogDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}