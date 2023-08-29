package toyproject.board;//package toyproject.board;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.stereotype.Component;
//import toyproject.board.dto.BoardDto;
//import toyproject.board.dto.MemberDto;
//import toyproject.board.repository.MemberRepository;
//import toyproject.board.service.BoardService;
//import toyproject.board.service.MemberService;
//
//@Component
//public class ApplicationRunner implements org.springframework.boot.ApplicationRunner {
//
//    @Autowired
//    MemberRepository memberRepository;
//    @Autowired
//    MemberService memberService;
//
//    @Autowired
//    BoardService boardService;
//
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//
//        MemberDto memberDto = MemberDto.builder()
//                .username("asd")
//                .password("123")
//                .email("aaaa")
//                .build();
//
//        memberService.joinUser(memberDto);
//
//        for(int i=1; i<80;i++) {
//            BoardDto boardDto = BoardDto.builder()
//                    .title("임시"+i)
//                    .content("완두콩의 블로그")
//                    .countVisit(1L)
//                    .createdBy("asd")
//                    .build();
//            boardService.saveBoard(boardDto);
//        }
//    }
//}
