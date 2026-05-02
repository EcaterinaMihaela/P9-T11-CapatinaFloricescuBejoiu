package com.example.demo.repository;

import com.example.demo.model.Member;

import java.util.Optional;

public interface MemberRepository extends BaseRepository<Member, Long> {
    Optional<Member> findByUser_Userid(Long userid);
}