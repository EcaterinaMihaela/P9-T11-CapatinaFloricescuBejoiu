package com.example.demo.controller;

import com.example.demo.dto.ReservationDTO;
import com.example.demo.model.Reservation;
import com.example.demo.service.ReservationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService service;

    public ReservationController(ReservationService service) {
        this.service = service;
    }

    @GetMapping
    public List<Reservation> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Reservation getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public Reservation create(@RequestBody ReservationDTO dto) {

        System.out.println("=== RESERVATION DTO RECEIVED ===");
        System.out.println("memberId = " + dto.getMemberId());
        System.out.println("bookId = " + dto.getBookId());
        System.out.println("status = " + dto.getStatus());
        System.out.println("date = " + dto.getReservationDate());

        return service.create(dto);
    }

    @PutMapping("/{id}")
    public Reservation update(@PathVariable Long id,
                              @RequestBody ReservationDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/member/{memberId}")
    public List<Reservation> getByMember(@PathVariable Long memberId) {
        return service.getByMember(memberId);
    }

    @PutMapping("/{id}/approve")
    public Reservation approve(@PathVariable Long id) {
        return service.approve(id);
    }

    @PutMapping("/{id}/reject")
    public Reservation reject(@PathVariable Long id) {
        return service.reject(id);
    }
}