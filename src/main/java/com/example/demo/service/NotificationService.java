package com.example.demo.service;

import com.example.demo.model.Notification;
import java.util.List;
public interface NotificationService {

    List<Notification> getAll();

    Notification getById(Long id);

    Notification create(Notification n);

    Notification update(Long id, Notification n);

    void delete(Long id);
}
