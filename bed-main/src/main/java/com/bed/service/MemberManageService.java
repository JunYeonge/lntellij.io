package com.bed.service;

//import com.bed.dto.MemberResponseDto;
import com.bed.dto.MemberSearchDto;
import com.bed.entity.Member;
import com.bed.exception.MemberManageException;
import com.bed.repository.CartRepository;
import com.bed.repository.MemberRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MemberManageService {

    private final MemberRepository memberRepository;

    public List<Member> index(){return memberRepository.findAll();}

    public Member show(Long id){
        return memberRepository.findById(id).orElse(null);

    }


//    public List<MemberResponseDto> makeResponseDto(List<Member> memberList) {
//        List<MemberResponseDto> memberListDtos = memberList.stream()
//                .map(member -> new MemberResponseDto(
//                        member.getId(),
//                        member.getProvider(),
//                        member.getRole(),
//                        member.getName(),
//                        member.getUsername(),
//                        member.getEmail(),
//                        member.getMerge_address(),
//                        member.getPhoneNumber(),
//                        member.getRegTime()))
//                .collect(Collectors.toList());
//        return memberListDtos;
//    }


//    public void delete(long id) {
//        Member member = memberRepository.findById(id).orElseThrow(() ->
//                new MemberManageException("사용자를 찾을 수 없습니다."));
//
//        memberRepository.deleteById(id);
//    }

    public void deleteSelectMembers(List<Long> selectedMembers){
        List<Member> members = memberRepository.findAllById(selectedMembers);
        memberRepository.deleteAll(members);
    }

    @Transactional(readOnly = true)
    public Page<Member> getMemberPage(MemberSearchDto memberSearchDto, Pageable pageable){
        return memberRepository.getMemberPage(memberSearchDto, pageable);
    }


//    @Transactional
//    public Member update(long memberId, MemberUpdateDto memberUpdateDto) {
//        Member member = memberRepository.findById(memberId).orElseThrow(() -> {
//            return new MemberManageException("사용자를 찾을 수 없습니다.");
//        });
//        member.changeMember(
//                memberUpdateDto.getAddress(),
//                memberUpdateDto.geteNikckname(),
//                memberUpdateDto.getPhonenumber()
//        );
//        return member;
//    }


}
