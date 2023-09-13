package webtoon.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import webtoon.dto.member.MemberSearchDto;
import webtoon.entity.member.Member;
import webtoon.repository.member.MemberRepository;
import webtoon.service.member.MemberManageService;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberManageController {

    private final MemberRepository memberRepository;
    private final MemberManageService memberManageService;


//    @GetMapping(value = {"/admin/members", "/admin/members/{page}"})
//    public String adminMembers(MemberSearchDto memberSearchDto,
//                               @PathVariable("page") Optional<Integer> page, Model model){
//        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);
//        Page<Member> members = memberManageService.getMemberPage(memberSearchDto, pageable);
//        model.addAttribute("memberList", members);
//        model.addAttribute("memberSearchDto", memberSearchDto);
//        model.addAttribute("maxPage", 5);
//        return "member/adminMember";
//    }
    @GetMapping(value = {"/admin/members", "/admin/members/{pageNumber}"})
    public String adminMembers(MemberSearchDto memberSearchDto,
                               @PathVariable("pageNumber") Optional<Integer> pageNumber, Model model){
        Pageable pageable = PageRequest.of(pageNumber.orElse(0), 5);
        Page<Member> members = memberManageService.getMemberPage(memberSearchDto, pageable);
        model.addAttribute("memberList", members);
        model.addAttribute("memberSearchDto", memberSearchDto);
        model.addAttribute("maxPage", 5);
        return "member/adminMember";
    }

//관리자페이지에서 멤버 선택하여 일괄 삭제하기
    @PostMapping("/admin/members/delete")
    public String deleteSelectMembers(@RequestParam("selectedMembers")List<Long> selectedMembers){
        // 선택된 게시물 삭제
        memberManageService.deleteSelectMembers(selectedMembers);

        //삭제 후 리다이렉트할 페이지로 이동
        return "redirect:/admin/members";
    }


}
