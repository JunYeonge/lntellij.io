package toyproject.board.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import toyproject.board.detail.PrincipalDetails;
import toyproject.board.domain.Member;
import toyproject.board.domain.Role;
import toyproject.board.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//시큐리티 설정에서 loginProcessingUrl("")
// login 요청이 오면 자동으로 UserDetailsService 타입으로 IoC 되어 있는 loadUserByUsername 함수가 실행.
@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    //username 이란 무엇인가? -> html 에서 input name 으로 값을 받을때 username 으로 동일해야 값이 받아짐. username2 로 html 에서 입력했으면 값이 안받아진다.
    // 시큐리티 세션 = Authentication(내부 member 들어감)
    // 시큐리티 세션 (Authentication(내부 member 들어감))
    // 함수 종료시 @AuthenticationPrincipal 어노테이션이 만들어진다.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username);
        if (member != null){
            return new PrincipalDetails(member);
        }
        return null;
    }
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<Member> findName = memberRepository.findByUsername(username);
//        Member member = findName.get();
//
//        List<GrantedAuthority> authorities = new ArrayList<>();
//
//        if(("admin").equals(username)){
//            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
//        }else {
//            authorities.add(new SimpleGrantedAuthority(Role.USER.getValue()));
//        }
////        return new User(member.getUsername(), member.getPassword() , authorities);
//        return new PrincipalDetails(member);
//    }

}
