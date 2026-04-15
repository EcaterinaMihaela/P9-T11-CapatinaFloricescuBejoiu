package com.example.demo.controller;

import com.example.demo.model.Member;
import com.example.demo.service.MemberService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService service;

    public MemberController(MemberService service) {
        this.service = service;
    }

    @GetMapping
    public List<Member> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Member getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public Member create(@RequestBody Member m) {
        return service.create(m);
    }

    @PutMapping("/{id}")
    public Member update(@PathVariable Long id, @RequestBody Member m) {
        return service.update(id, m);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}