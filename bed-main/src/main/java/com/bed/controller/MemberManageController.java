package com.bed.controller;

//import com.bed.dto.MemberResponseDto;
import com.bed.dto.MemberSearchDto;
import com.bed.entity.Article;
import com.bed.entity.Member;
import com.bed.repository.MemberRepository;
import com.bed.service.MemberManageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberManageController {

    private final MemberRepository memberRepository;
    private final MemberManageService memberManageService;


    @GetMapping(value = {"/admin/members", "/admin/members/{page}"})
    public String adminMembers(MemberSearchDto memberSearchDto,
                               @PathVariable("page") Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);
        Page<Member> members = memberManageService.getMemberPage(memberSearchDto, pageable);
        model.addAttribute("memberList", members);
        model.addAttribute("memberSearchDto", memberSearchDto);
        model.addAttribute("maxPage", 5);
        return "member/adminMember";
    }

//    @DeleteMapping("api/members/{memberId}")
//    public ResponseEntity<CMResponse> deleteMember(@PathVariable Long memberId){
//        memberManageService.deleteMember(memberId);
//        return new ResponseEntity(new CMResponse<>(1, "회원 탈퇴 성공", ""),HttpStatus.OK);
//    }




//    @GetMapping("/admin/members")
//    public String memberList(Model model){
//        List<Member> memberList = memberRepository.findAll();
//        List<MemberResponseDto> memberListDto = memberManageService.makeResponseDto(memberList);
//        model.addAttribute("memberListDto", memberListDto);
//        return "member/adminMember";
//    }


//    검색 관련

//    @GetMapping("/admin/search/member")
//    public String searchMember(@RequestParam(value = "keyword") String keyword, Model model){
//        List<Member> memberList = memberRepository.searchByKeyword(keyword);
//        List<MemberResponseDto> memberListDto = memberManageService.makeResponseDto(memberList);
//        model.addAttribute("memberListDto", memberListDto);
//        return "member/adminMember";
//    }

//    관리자페이지에서 멤버 삭제하기
//    @GetMapping("/admin/members/{id}/delete")
//    public String deleteMember(@PathVariable Long id, RedirectAttributes rttr){
//        Member target = memberRepository.findById(id).orElse(null);
//        if(target!=null){
//            memberRepository.delete(target);
//            rttr.addFlashAttribute("msg","삭제되었습니다.");
//        }
//        return "redirect:/admin/members";
//    }
//관리자페이지에서 멤버 선택하여 일괄 삭제하기
    @PostMapping("/admin/members/delete")
    public String deleteSelectMembers(@RequestParam("selectedMembers")List<Long> selectedMembers){
        // 선택된 게시물 삭제
        memberManageService.deleteSelectMembers(selectedMembers);

        //삭제 후 리다이렉트할 페이지로 이동
        return "redirect:/admin/members";
    }


}
