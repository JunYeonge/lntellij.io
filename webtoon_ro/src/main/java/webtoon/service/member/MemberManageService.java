package webtoon.service.member;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webtoon.dto.member.MemberSearchDto;
import webtoon.entity.member.Member;
import webtoon.repository.member.MemberRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberManageService {

    private final MemberRepository memberRepository;

    public List<Member> index(){return memberRepository.findAll();}

    public Member show(Long id){
        return memberRepository.findById(id).orElse(null);

    }

    public void deleteSelectMembers(List<Long> selectedMembers){
        List<Member> members = memberRepository.findAllById(selectedMembers);
        memberRepository.deleteAll(members);
    }

    @Transactional(readOnly = true)
    public Page<Member> getMemberPage(MemberSearchDto memberSearchDto, Pageable pageable){
        return memberRepository.getMemberPage(memberSearchDto, pageable);
    }




}
