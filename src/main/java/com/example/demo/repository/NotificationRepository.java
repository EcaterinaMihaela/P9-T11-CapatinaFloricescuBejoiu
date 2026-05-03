package com.example.demo.repository;

import com.example.demo.model.Notification;
import com.example.demo.model.User;

import java.time.LocalDate;
import java.util.List;

public interface NotificationRepository extends BaseRepository<Notification, Long> {
    List<Notification> findByUser_UsernameOrderBySendingDateDesc(String username);
    boolean existsByUserAndMessageAndSendingDate(User user, String message, LocalDate sendingDate);
}