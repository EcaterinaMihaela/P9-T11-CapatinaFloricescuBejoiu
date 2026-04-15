package com.example.demo.service.impl;

import com.example.demo.model.Reservation;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.service.ReservationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository repo;

    public ReservationServiceImpl(ReservationRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Reservation> getAll() {
        return repo.findAll();
    }

    @Override
    public Reservation getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Reservation create(Reservation reservation) {
        return repo.save(reservation);
    }

    @Override
    public Reservation update(Long id, Reservation reservation) {
        return repo.findById(id).map(r -> {
            r.setReservationDate(reservation.getReservationDate());
            r.setStatus(reservation.getStatus());
            r.setMember(reservation.getMember());
            r.setBook(reservation.getBook());
            return repo.save(r);
        }).orElse(null);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}