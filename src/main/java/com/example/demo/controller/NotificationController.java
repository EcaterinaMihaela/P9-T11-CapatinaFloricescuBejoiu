package com.example.demo.controller;

import com.example.demo.model.Notification;
import com.example.demo.service.NotificationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }

    @GetMapping
    public List<Notification> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Notification getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public Notification create(@RequestBody Notification n) {
        return service.create(n);
    }

    @PutMapping("/{id}")
    public Notification update(@PathVariable Long id, @RequestBody Notification n) {
        return service.update(id, n);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}