package com.example.demo.service.impl;

import com.example.demo.dto.MemberDTO;
import com.example.demo.model.Member;
import com.example.demo.repository.RepositoryWrapper;
import com.example.demo.service.MemberService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    private final RepositoryWrapper repo;

    public MemberServiceImpl(RepositoryWrapper repo) {
        this.repo = repo;
    }

    @Override
    public List<Member> getAll() {
        return repo.member.findAllSafe();
    }

    @Override
    public Member getById(Long id) {
        return repo.member.findByIdSafe(id).orElse(null);
    }

    @Override
    public Member create(MemberDTO dto) {

        Member m = new Member();
        m.setBorrowLimit(dto.getBorrowLimit());
        m.setStatus(dto.getStatus());
        m.setBanReason(dto.getBanReason());

        return repo.member.saveSafe(m);
    }

    @Override
    public Member update(Long id, MemberDTO dto) {

        return repo.member.findByIdSafe(id).map(m -> {

            m.setBorrowLimit(dto.getBorrowLimit());
            m.setStatus(dto.getStatus());
            m.setBanReason(dto.getBanReason());

            return repo.member.saveSafe(m);

        }).orElse(null);
    }

    @Override
    public void delete(Long id) {
        repo.member.deleteSafe(id);
    }
}