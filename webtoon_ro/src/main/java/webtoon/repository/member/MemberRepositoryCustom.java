package webtoon.repository.member;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import webtoon.dto.member.MemberSearchDto;
import webtoon.entity.member.Member;

public interface MemberRepositoryCustom {
    Page<Member> getMemberPage(MemberSearchDto memberSearchDto, Pageable pageable);
}
