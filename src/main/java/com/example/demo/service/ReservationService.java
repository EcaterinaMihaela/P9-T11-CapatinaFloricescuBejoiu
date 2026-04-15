package com.example.demo.service;

import com.example.demo.model.Reservation;
import java.util.List;

public interface ReservationService {

    List<Reservation> getAll();

    Reservation getById(Long id);

    Reservation create(Reservation reservation);

    Reservation update(Long id, Reservation reservation);

    void delete(Long id);
}