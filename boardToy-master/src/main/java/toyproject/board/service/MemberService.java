package toyproject.board.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.board.domain.Member;
import toyproject.board.domain.Role;
import toyproject.board.dto.MemberDto;
import toyproject.board.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MemberService{

    private final MemberRepository memberRepository;

    /**
     * 중복 아이디 검증
     */
//    public void validateDuplicateId(Member member){
//        List<Member> findMembers = memberRepository.findByUsername(member.getUsername());
//        if(!findMembers.isEmpty()){
//            throw new IllegalStateException("이미 존재하는 이름입니다. 다른 이름을 입력해주세요.");
//        }
//    }
    public List<Member> findAll(){
        return memberRepository.findAll();
    }

    public Member findByUsername(String username){
        return memberRepository.findByUsername(username);
    }

//    public void updateMember(Member member){
//        memberRepository.update(member.getUsername(), member.getId());
//    }

    @Transactional
    public Long joinUser(MemberDto memberDto) {
        // 비밀번호 암호화
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));

        return memberRepository.save(memberDto.toEntity()).getId();
    }

    @Transactional
    public Long joinUserWithMember(Member member) {
        // 비밀번호 암호화
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        member.setPassword(passwordEncoder.encode(memberDto.getPassword()));

        return memberRepository.save(member).getId();
    }
}
