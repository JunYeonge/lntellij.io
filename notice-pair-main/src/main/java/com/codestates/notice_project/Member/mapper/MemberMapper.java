package com.codestates.notice_project.Member.mapper;

import com.codestates.notice_project.Member.dto.MemberDto;
import com.codestates.notice_project.Member.entity.Member;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    Member memberPostDtoToMember(MemberDto.Post requestBody);
    Member memberPatchDtoToMember(MemberDto.Patch requestBody);
    MemberDto.Response memberToMemberResponse(Member member);
    List<MemberDto.Response> membersToMemberResponse(List<Member> members);
}
