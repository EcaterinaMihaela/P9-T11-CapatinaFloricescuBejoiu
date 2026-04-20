package com.example.demo.service.impl;

import com.example.demo.model.Notification;
import com.example.demo.repository.NotificationRepository;
import com.example.demo.service.NotificationService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository repo;

    public NotificationServiceImpl(NotificationRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Notification> getAll() {
        return repo.findAll();
    }

    @Override
    public Notification getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Notification create(Notification n) {
        return repo.save(n);
    }

    @Override
    public Notification update(Long id, Notification n) {
        return repo.findById(id).map(notif -> {

            notif.setSendingDate(n.getSendingDate());
            notif.setSendingTime(n.getSendingTime());
            notif.setType(n.getType());
            notif.setMessage(n.getMessage());
            notif.setUser(n.getUser());

            return repo.save(notif);

        }).orElse(null);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}