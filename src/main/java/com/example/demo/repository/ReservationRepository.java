package com.example.demo.repository;

import com.example.demo.model.Reservation;

import java.util.List;

public interface ReservationRepository extends BaseRepository<Reservation, Long> {
    List<Reservation> findByMember_MemberID(Long memberId);
}