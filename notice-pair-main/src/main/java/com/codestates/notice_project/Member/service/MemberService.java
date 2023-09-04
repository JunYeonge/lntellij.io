package com.codestates.notice_project.Member.service;

import com.codestates.notice_project.Member.entity.Member;
import com.codestates.notice_project.Member.repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * - 메서드 구현
 */
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // 회원 가입
    public Member createMember(Member member) {

        //이미 존재하는 회원인지 확인
        verifyExistsEmail(member.getEmail());
        return memberRepository.save(member);
    }

    // 회원 정보 수정
    public Member updateMember(Member member) {

        // 존재하는 회원인지 확인
        Member findMember = findVerifiedMember(member.getMemberId());

        // 수정 사항이 있으면 데이터 변경
        Optional.ofNullable(member.getName()).ifPresent(name -> findMember.setName(name));
        Optional.ofNullable(member.getPassword()).ifPresent(password -> findMember.setPassword(password));
        Optional.ofNullable(member.getMemberStatus()).ifPresent(memberStatus -> findMember.setMemberStatus(memberStatus));

        return memberRepository.save(findMember);
    }

    // 멤버 조회
    public Member findMember(long memberId) {
        return findVerifiedMember(memberId);
    }

    // 멤버 목록 조회
    public Page<Member> findMembers(int page, int size) {
        return memberRepository.findAll(PageRequest.of(page, size, Sort.by("memberId").descending()));
    }

    // 멤버 삭제
    public void deleteMember(long memberId) {
        // 존재하는 멤버인지 확인
        Member findMember = findVerifiedMember(memberId);
        memberRepository.delete(findMember);
    }

    // 존재하는 회원이 맞는지 검증
    public Member findVerifiedMember(long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        Member findMember = optionalMember.orElseThrow(() -> new RuntimeException("MEMBER_NOT_FOUND"));

        return findMember;
    }

    // 이미 존재하는 회원인지 검증
    private void verifyExistsEmail(String email){
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isPresent())
            throw new RuntimeException("MEMBER_EXISTS");
    }
}
