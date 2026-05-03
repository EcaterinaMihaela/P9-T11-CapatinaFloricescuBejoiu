package com.example.demo.service;

import com.example.demo.dto.NotificationDTO;
import com.example.demo.model.Notification;

import java.util.List;

public interface NotificationService {

    List<Notification> getAll();

    Notification getById(Long id);

    Notification create(NotificationDTO dto);

    Notification update(Long id, NotificationDTO dto);

    void delete(Long id);

    List<Notification> getByUsername(String username);
    void updateStatus(Notification n);
}