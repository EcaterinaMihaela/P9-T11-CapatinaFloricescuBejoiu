package com.example.demo.service.impl;

import com.example.demo.dto.ReportDTO;
import com.example.demo.model.Report;
import com.example.demo.model.User;
import com.example.demo.repository.impl.RepositoryWrapper;
import com.example.demo.service.ReportService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {

    private final RepositoryWrapper repo;

    public ReportServiceImpl(RepositoryWrapper repo) {
        this.repo = repo;
    }

    @Override
    public List<Report> getAll() {
        return repo.report.findAll();
    }

    @Override
    public Report getById(Long id) {
        return repo.report.findById(id).orElse(null);
    }

    @Override
    public Report create(ReportDTO dto) {

        User user = repo.user.findById(dto.getUserID()).orElse(null);

        Report r = new Report();
        r.setType(dto.getType());
        r.setGenerationDate(dto.getGenerationDate());
        r.setGenerationTime(dto.getGenerationTime());
        r.setFormat(dto.getFormat());
        r.setUser(user);

        return repo.report.save(r);
    }

    @Override
    public Report update(Long id, ReportDTO dto) {

        return repo.report.findById(id).map(r -> {

            r.setType(dto.getType());
            r.setGenerationDate(dto.getGenerationDate());
            r.setGenerationTime(dto.getGenerationTime());
            r.setFormat(dto.getFormat());

            User user = repo.user.findById(dto.getUserID()).orElse(null);
            r.setUser(user);

            return repo.report.save(r);

        }).orElse(null);
    }

    @Override
    public void delete(Long id) {
        repo.report.deleteById(id);
    }
    // În ReportServiceImpl.java

    // În ReportServiceImpl.java

    @Override
    public Map<String, Object> getDashboardData(String month) {
        Map<String, Object> data = new HashMap<>();
        LocalDate today = LocalDate.now();

        // Luăm toate împrumuturile (Loan)
        List<com.example.demo.model.Loan> allLoans = repo.loan.findAllSafe();

        // --- LOGICA DE FILTRARE ---
        // Dacă 'month' nu este null sau gol, filtrăm listele
        if (month != null && !month.isEmpty()) {
            allLoans = allLoans.stream()
                    .filter(loan -> loan.getBorrowDate() != null &&
                            loan.getBorrowDate().toString().startsWith(month))
                    .collect(java.util.stream.Collectors.toList());
        }

        // 1. Cifre calculate pe baza listei filtrate (sau totale dacă month e null)
        data.put("totalLoans", allLoans.size());

        // Calculăm utilizatorii activi care au împrumuturi în luna respectivă
        long activeUsersInMonth = allLoans.stream()
                .map(loan -> loan.getMember())
                .distinct()
                .count();
        data.put("activeUsers", activeUsersInMonth);

        // 2. Top cărți în luna selectată
        Map<String, Long> bookCounts = allLoans.stream()
                .collect(java.util.stream.Collectors.groupingBy(
                        loan -> loan.getBook().getBookTitle(),
                        java.util.stream.Collectors.counting()
                ));

        List<String> topBooks = bookCounts.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(3)
                .map(e -> e.getKey() + " (" + e.getValue() + " borrows)")
                .collect(java.util.stream.Collectors.toList());

        data.put("topBooks", topBooks);

        // 3. Overdue Returns
        List<String> overdue = repo.loan.findAllSafe().stream()
                .filter(l -> l.getReturnDate() == null && l.getDueDate().isBefore(today))
                .map(l -> {
                    String name = (l.getMember() != null && l.getMember().getUser() != null)
                            ? l.getMember().getUser().getUsername() : "Unknown";
                    return name + " - " + l.getBook().getBookTitle() + " (Due: " + l.getDueDate() + ")";
                })
                .collect(java.util.stream.Collectors.toList());

        data.put("overdueReturns", overdue);


        data.put("totalReservations", repo.reservation.findAllSafe().size());
        data.put("newBooks", repo.book.findAllSafe().size());

        return data;
    }
}