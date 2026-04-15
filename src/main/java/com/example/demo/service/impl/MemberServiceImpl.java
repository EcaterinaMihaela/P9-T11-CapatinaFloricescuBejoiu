package com.example.demo.service.impl;

import com.example.demo.model.Member;
import com.example.demo.repository.MemberRepository;
import com.example.demo.service.MemberService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository repo;

    public MemberServiceImpl(MemberRepository repo) {
        this.repo = repo;
    }

    public List<Member> getAll() { return repo.findAll(); }

    public Member getById(Long id) { return repo.findById(id).orElse(null); }

    public Member create(Member m) { return repo.save(m); }

    public Member update(Long id, Member m) {
        return repo.findById(id).map(x -> {
            x.setBorrowLimit(m.getBorrowLimit());
            x.setStatus(m.getStatus());
            x.setBanReason(m.getBanReason());
            return repo.save(x);
        }).orElse(null);
    }

    public void delete(Long id) { repo.deleteById(id); }
}