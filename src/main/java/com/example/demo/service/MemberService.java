package com.example.demo.service;

import com.example.demo.dto.MemberDTO;
import com.example.demo.model.Member;

import java.util.List;

public interface MemberService {

    List<Member> getAll();

    Member getById(Long id);

    Member create(MemberDTO dto);

    Member update(Long id, MemberDTO dto);

    void delete(Long id);
}