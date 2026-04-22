package com.example.demo.service.impl;

import com.example.demo.dto.ReservationDTO;
import com.example.demo.model.Book;
import com.example.demo.model.Member;
import com.example.demo.model.Reservation;
import com.example.demo.repository.RepositoryWrapper;
import com.example.demo.service.ReservationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final RepositoryWrapper repo;

    public ReservationServiceImpl(RepositoryWrapper repo) {
        this.repo = repo;
    }

    @Override
    public List<Reservation> getAll() {
        return repo.reservation.findAll();
    }

    @Override
    public Reservation getById(Long id) {
        return repo.reservation.findById(id).orElse(null);
    }

    @Override
    public Reservation create(ReservationDTO dto) {

        Member member = repo.member.findById(dto.getMemberId()).orElse(null);
        Book book = repo.book.findById(dto.getBookId()).orElse(null);

        Reservation r = new Reservation();
        r.setReservationDate(dto.getReservationDate());
        r.setStatus(dto.getStatus());
        r.setMember(member);
        r.setBook(book);

        return repo.reservation.save(r);
    }

    @Override
    public Reservation update(Long id, ReservationDTO dto) {

        return repo.reservation.findById(id).map(r -> {

            r.setReservationDate(dto.getReservationDate());
            r.setStatus(dto.getStatus());

            Member member = repo.member.findById(dto.getMemberId()).orElse(null);
            Book book = repo.book.findById(dto.getBookId()).orElse(null);

            r.setMember(member);
            r.setBook(book);

            return repo.reservation.save(r);

        }).orElse(null);
    }

    @Override
    public void delete(Long id) {
        repo.reservation.deleteById(id);
    }
}