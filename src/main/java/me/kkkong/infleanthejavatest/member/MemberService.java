package me.kkkong.infleanthejavatest.member;

import me.kkkong.infleanthejavatest.domain.Member;

import java.util.Optional;

public interface MemberService {
    Optional<Member> findById(Long memberId);
}
