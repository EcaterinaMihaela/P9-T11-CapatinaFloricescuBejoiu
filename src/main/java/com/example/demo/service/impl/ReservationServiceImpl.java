package com.example.demo.service.impl;

import com.example.demo.dto.ReservationDTO;
import com.example.demo.model.Book;
import com.example.demo.model.Member;
import com.example.demo.model.Reservation;
import com.example.demo.repository.impl.RepositoryWrapper;
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

        Member member = repo.member.findById(dto.getMemberId())
                .orElseThrow(() -> new RuntimeException("Member not found"));

        Book book = repo.book.findById(dto.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        Reservation r = new Reservation();
        r.setReservationDate(dto.getReservationDate());
        r.setStatus("PENDING");
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
    public List<Reservation> getByMember(Long memberId) {
        return repo.reservation.findByMember_MemberID(memberId);
    }

    @Override
    public Reservation approve(Long id) {

        return repo.reservation.findById(id).map(r -> {

            if (!"PENDING".equals(r.getStatus())) {
                return r;
            }

            Book book = r.getBook();

            if (book.getAvailableStock() <= 0) {
                throw new RuntimeException("Book not available");
            }

            book.setAvailableStock(book.getAvailableStock() - 1);
            repo.book.save(book);

            r.setStatus("APPROVED");

            return repo.reservation.save(r);

        }).orElse(null);
    }

    @Override
    public Reservation reject(Long id) {

        return repo.reservation.findById(id).map(r -> {

            if (!"PENDING".equals(r.getStatus())) {
                return r;
            }

            r.setStatus("REJECTED");

            return repo.reservation.save(r);

        }).orElse(null);
    }
}