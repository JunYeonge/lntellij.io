package toyproject.board.detail;//package toyproject.board.detail;
//
//import lombok.Getter;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import toyproject.board.domain.Member;
//
//import java.util.ArrayList;
//import java.util.Collection;
//
//@Getter
//public class MemberDetail implements UserDetails {
//
////    private Long id;
////    private String username;
////    private String password;
////    private String email;
////    private Collection<GrantedAuthority> authorities;
//
//    private Member member;
//    public MemberDetail(Member member) {
//        this.member = member;
//    }
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        Collection<GrantedAuthority> collect = new ArrayList<>();
//        collect.add(new GrantedAuthority() {
//            @Override
//            public String getAuthority() {
//                return member.getRoleKey();
//            }
//        });
//        return collect;
//    }
//
//    @Override
//    public String getPassword() {
//        return member.getPassword();
//    }
//
//    @Override
//    public String getUsername() {
//        return member.getUsername();
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//}
