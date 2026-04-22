package com.example.demo.service.impl;

import com.example.demo.dto.NotificationDTO;
import com.example.demo.model.Notification;
import com.example.demo.model.User;
import com.example.demo.repository.RepositoryWrapper;
import com.example.demo.service.NotificationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final RepositoryWrapper repo;

    public NotificationServiceImpl(RepositoryWrapper repo) {
        this.repo = repo;
    }

    @Override
    public List<Notification> getAll() {
        return repo.notification.findAll();
    }

    @Override
    public Notification getById(Long id) {
        return repo.notification.findById(id).orElse(null);
    }

    @Override
    public Notification create(NotificationDTO dto) {

        User user = repo.user.findById(dto.getUserID()).orElse(null);

        Notification n = new Notification();
        n.setSendingDate(dto.getSendingDate());
        n.setSendingTime(dto.getSendingTime());
        n.setType(dto.getType());
        n.setMessage(dto.getMessage());
        n.setUser(user);

        return repo.notification.save(n);
    }

    @Override
    public Notification update(Long id, NotificationDTO dto) {

        return repo.notification.findById(id).map(n -> {

            n.setSendingDate(dto.getSendingDate());
            n.setSendingTime(dto.getSendingTime());
            n.setType(dto.getType());
            n.setMessage(dto.getMessage());

            User user = repo.user.findById(dto.getUserID()).orElse(null);
            n.setUser(user);

            return repo.notification.save(n);

        }).orElse(null);
    }

    @Override
    public void delete(Long id) {
        repo.notification.deleteById(id);
    }
}