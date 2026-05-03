package com.example.demo.controller;

import com.example.demo.dto.NotificationDTO;
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
    public Notification create(@RequestBody NotificationDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public Notification update(@PathVariable Long id,
                               @RequestBody NotificationDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
    @GetMapping("/my-notifications")
    public List<Notification> getMyNotifications(@RequestParam("username") String username) {

        return service.getByUsername(username);
    }
    @PostMapping("/mark-all-read")
    public void markAllAsRead(@RequestParam("username") String username) {
        List<Notification> notifications = service.getByUsername(username);
        for (Notification n : notifications) {
            n.setRead(true);

            service.updateStatus(n);
        }
    }
}