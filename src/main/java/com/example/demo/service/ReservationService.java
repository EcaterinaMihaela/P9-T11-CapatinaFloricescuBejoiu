package com.example.demo.service;

import com.example.demo.dto.ReservationDTO;
import com.example.demo.model.Reservation;

import java.util.List;

public interface ReservationService {

    List<Reservation> getAll();

    Reservation getById(Long id);

    Reservation create(ReservationDTO dto);

    Reservation update(Long id, ReservationDTO dto);

    void delete(Long id);

    List<Reservation> getByMember(Long memberId);

    Reservation approve(Long id);
    Reservation reject(Long id);
}