package webtoon.controller.member;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import webtoon.dto.member.MemberSearchDto;
import webtoon.entity.member.Member;
import webtoon.repository.member.MemberRepository;
import webtoon.service.member.MemberManageService;

import java.util.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberManageController {

    private final MemberManageService memberManageService;

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

    @PostMapping("/admin/members/delete")
    public String deleteSelectMembers(@RequestParam(name = "selectedMembers", required = false) List<Long> selectedMembers, RedirectAttributes redirectAttributes) {
        if (selectedMembers == null || selectedMembers.isEmpty()) {
            // 선택된 회원이 없을 경우 예외 처리
            // 여기에서 원하는 예외 처리 로직을 추가하거나 사용자에게 알리는 메시지를 설정할 수 있습니다.
            String errorMessage = "선택된 회원이 없습니다. 삭제할 회원을 선택하세요.";
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return "redirect:/admin/members";
        }

        // 선택된 게시물 삭제
        memberManageService.deleteSelectMembers(selectedMembers);

        // 리다이렉트할 페이지로 이동
        return "redirect:/admin/members";
    }


}