package com.example.demo.controller;

import com.example.demo.dto.MemberDTO;
import com.example.demo.model.Member;
import com.example.demo.service.MemberService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members") 
public class MemberProfileController {

    private final MemberService service;

    public MemberProfileController(MemberService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public Member getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public Member update(@PathVariable Long id, @RequestBody MemberDTO dto) {
        return service.update(id, dto);
    }

    @GetMapping
    public List<Member> getAll() {
        return service.getAll();
    }
}