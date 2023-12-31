package com.cnu.sw2023.member.repository;

import com.cnu.sw2023.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {

    boolean existsByEmail (String email);

    Optional<Member> findByMemberId(String memberId);

    boolean existsByMemberId(String memberId);

}
