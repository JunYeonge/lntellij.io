package com.codestates.notice_project.Member.controller;

import com.codestates.notice_project.Member.dto.MemberDto;
import com.codestates.notice_project.Member.entity.Member;
import com.codestates.notice_project.Member.mapper.MemberMapper;
import com.codestates.notice_project.Member.service.MemberService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/members")
@Validated
public class MemberController {

    private final MemberService memberService;
    private final MemberMapper mapper;

    public MemberController(MemberService memberService, MemberMapper mapper) {
        this.memberService = memberService;
        this.mapper = mapper;
    }

    //Member 생성
    @PostMapping
    public ResponseEntity postMember(@Valid @RequestBody MemberDto.Post requestBody) {

        Member member = mapper.memberPostDtoToMember(requestBody);
        Member createMember = memberService.createMember(member);
        MemberDto.Response response = mapper.memberToMemberResponse(createMember);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //Member 수정
    @PatchMapping("/{member-id}")
    public ResponseEntity patchMember(@PathVariable("member-id") @Positive long memberId,
                                      @Valid @RequestBody MemberDto.Patch requestBody) {

        requestBody.setMemberId(memberId);
        Member member = mapper.memberPatchDtoToMember(requestBody);
        Member updateMember = memberService.updateMember(member);
        MemberDto.Response response = mapper.memberToMemberResponse(updateMember);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Member 조회
    @GetMapping("/{member-id}")
    public ResponseEntity getMember(@PathVariable("member-id") @Positive long memberId) {

        Member findMember = memberService.findMember(memberId);
        MemberDto.Response response = mapper.memberToMemberResponse(findMember);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Member 목록 조회
    @GetMapping
    public ResponseEntity getMembers(@Positive @RequestParam int page,
                                     @Positive @RequestParam int size) {

        Page<Member> members = memberService.findMembers(page - 1, size);
        List<Member> memberList = members.getContent();
        List<MemberDto.Response> responses = mapper.membersToMemberResponse(memberList);

        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    //Member 삭제
    @DeleteMapping("/{member-id}")
    public ResponseEntity deleteMember(@PathVariable("member-id") long memberId) {

        memberService.deleteMember(memberId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
