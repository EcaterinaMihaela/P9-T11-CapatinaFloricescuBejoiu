package com.example.demo.service;

import com.example.demo.model.Member;
import java.util.List;

public interface MemberService {
    List<Member> getAll();
    Member getById(Long id);
    Member create(Member m);
    Member update(Long id, Member m);
    void delete(Long id);
}