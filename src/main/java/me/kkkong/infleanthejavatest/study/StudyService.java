package me.kkkong.infleanthejavatest.study;

import me.kkkong.infleanthejavatest.domain.Member;
import me.kkkong.infleanthejavatest.domain.Study;
import me.kkkong.infleanthejavatest.member.MemberService;

import java.util.Optional;

public class StudyService {
    private final MemberService memberService;
    private final StudyRepository repository;

    public StudyService(MemberService memberService, StudyRepository repository) {
        assert memberService != null;
        assert repository != null;
        this.memberService = memberService;
        this.repository = repository;
    }

    public Study createNewStudy(Long memberId, Study study) {
        Optional<Member> member = memberService.findById(memberId);
        study.setOwner(member.orElseThrow(() -> new IllegalArgumentException("Member does not found.")));
        return repository.save(study);
    }
}
