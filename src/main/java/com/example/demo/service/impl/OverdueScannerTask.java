package com.example.demo.service.impl;

import com.example.demo.model.Loan;
import com.example.demo.model.Notification;
import com.example.demo.model.User;
import com.example.demo.repository.LoanRepository;
import com.example.demo.repository.NotificationRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class OverdueScannerTask {

    private final LoanRepository loanRepository;
    private final NotificationRepository notificationRepository;

    public OverdueScannerTask(LoanRepository loanRepository, NotificationRepository notificationRepository) {
        this.loanRepository = loanRepository;
        this.notificationRepository = notificationRepository;
    }

    @jakarta.transaction.Transactional // Pasul 1: Asigură salvarea în baza de date
    @Scheduled(fixedRate = 60000) // Rulează la fiecare minut
    public void scanLoans() {
        List<Loan> allLoans = loanRepository.findAll();
        LocalDate today = LocalDate.now();

        for (Loan loan : allLoans) {
            if (loan.getReturnDate() == null) { // Doar pentru cărți nereturnate

                // CAZ 1: RESTANȚĂ (Data scadentă a trecut)
                if (today.isAfter(loan.getDueDate())) {
                    String msg = "OVERRUN: You are overdue for '" + loan.getBook().getBookTitle() + "'.";
                    saveNewNotification(loan, msg, "OVERDUE");
                }

                // CAZ 2: AZI (Data scadentă este chiar astăzi)
                else if (today.equals(loan.getDueDate())) {
                    String msg = "LAST DAY: Today you have to return '" + loan.getBook().getBookTitle() + "'!";
                    saveNewNotification(loan, msg, "DUE_TODAY");
                }

                // CAZ 3: O ZI (Mai este exact o zi)
                else if (today.plusDays(1).equals(loan.getDueDate())) {
                    String msg = "REMINDER: You have one more day to return. '" + loan.getBook().getBookTitle() + "'.";
                    saveNewNotification(loan, msg, "REMINDER");
                }

                // CAZ 4: DOUĂ ZILE (Mai sunt exact 2 zile)
                else if (today.plusDays(2).equals(loan.getDueDate())) {
                    String msg = "REMINDER: You have 2 more days to return. '" + loan.getBook().getBookTitle() + "'.";
                    saveNewNotification(loan, msg, "REMINDER");
                }
            }
        }
    }

    private void saveNewNotification(Loan loan, String message, String type) {
        User user = loan.getMember().getUser();
        LocalDate today = LocalDate.now();

        // Verifică dacă am trimis DEJA acest mesaj AZI
        boolean alreadyExists = notificationRepository.existsByUserAndMessageAndSendingDate(
                user, message, today);

        if (!alreadyExists) {
            Notification notif = new Notification();
            notif.setSendingDate(today);
            notif.setSendingTime(LocalTime.now());
            notif.setType(type);
            notif.setMessage(message);
            notif.setUser(user);

            notificationRepository.save(notif);
            System.out.println("✅ Notification " + type + " send for: " + user.getUsername());
        } else {
            // Nu face nimic, mesajul există deja în DB pentru ziua de azi
            System.out.println("Skipping duplicate for: " + user.getUsername());
        }
    }
}