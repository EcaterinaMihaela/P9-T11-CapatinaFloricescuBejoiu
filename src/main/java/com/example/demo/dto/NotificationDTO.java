package com.example.demo.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class NotificationDTO {

    private Long notificationID;

    private LocalDate sendingDate;
    private LocalTime sendingTime;

    private String type;
    private String message;

    private Long userID;

    public NotificationDTO() {}

    public NotificationDTO(Long notificationID,
                           LocalDate sendingDate,
                           LocalTime sendingTime,
                           String type,
                           String message,
                           Long userID) {
        this.notificationID = notificationID;
        this.sendingDate = sendingDate;
        this.sendingTime = sendingTime;
        this.type = type;
        this.message = message;
        this.userID = userID;
    }

    // getters & setters
    public Long getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(Long notificationID) {
        this.notificationID = notificationID;
    }

    public LocalDate getSendingDate() {
        return sendingDate;
    }

    public void setSendingDate(LocalDate sendingDate) {
        this.sendingDate = sendingDate;
    }

    public LocalTime getSendingTime() {
        return sendingTime;
    }

    public void setSendingTime(LocalTime sendingTime) {
        this.sendingTime = sendingTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }
}