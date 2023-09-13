package webtoon.service.member;


import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.thymeleaf.util.StringUtils;
import webtoon.config.CustomUserDetails;
import webtoon.dto.member.MemberEditFormDto;
import webtoon.dto.member.MemberInfoFormDto;
import webtoon.entity.member.Member;
import webtoon.repository.member.MemberRepository;

import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
@Transactional
//@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(MemberService.class);

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;

    }
    public boolean isNicknameDuplicate(String nickname) {
        Member existingMember = memberRepository.findByNickname(nickname);
        return existingMember != null;
    }

    public boolean isEmailDuplicate(String email) {
        Member existingMember = memberRepository.findByEmail(email);
        return existingMember != null;
    }
    public Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public Member saveMember(Member member) {
        // 주민등록번호 처리 및 나이, 성별 계산
        calculateAgeAndGender(member);

        // 닉네임이 비어있을 경우 이메일의 '@' 앞 부분을 사용
        if (StringUtils.isEmpty(member.getNickname())) {
            String email = member.getEmail();
            int atIndex = email.indexOf("@");
            if (atIndex != -1) {
                String nickname = email.substring(0, atIndex);
                member.setNickname(nickname);
            }
        }
        // 회원 정보 저장 전에 닉네임과 이메일 중복 검사
        if (isNicknameDuplicate(member.getNickname())) {
            throw new IllegalStateException("닉네임이 이미 사용 중입니다.");
        }
        if (isEmailDuplicate(member.getEmail())) {
            throw new IllegalStateException("이미 가입된 이메일 주소입니다.");
        }

        // 회원 정보 저장
        return memberRepository.save(member);
    }

    // 다른 서비스 로직들...


    private void calculateAgeAndGender(Member member) {
        if (member == null || member.getResidentIdFront() == null || member.getResidentIdBack() == null) {
            throw new IllegalArgumentException("Member 또는 주민등록번호가 null입니다.");
        }

        String residentIdFront = member.getResidentIdFront();
        String residentIdBack = member.getResidentIdBack();

        // 주민등록번호 앞자리로 나이 계산
        int birthYear = Integer.parseInt(residentIdFront.substring(0, 2));
        int currentYear = java.time.LocalDate.now().getYear() % 100;
        if (birthYear > currentYear) {
            currentYear += 100;
        }
        int age = currentYear - birthYear + 1;

        // 주민등록번호 뒷자리로 성별 계산
        String genderDigit = residentIdBack.substring(0, 1);
        System.out.println(genderDigit);
        String gender = (genderDigit.equals("1") || genderDigit.equals("3")) ? "male" : "female";

        // 나이와 성별을 엔티티에 설정
        member.setAge(age);
        member.setGender(gender);
    }


    public void updateMember(Authentication authentication,
                             MemberEditFormDto memberEditFormDto,
                             BindingResult bindingResult) {
        String email = authentication.getName();

        Member member = memberRepository.findByEmail(email);
        if (member != null) {
            boolean nicknameChanged = false;
            if (!memberEditFormDto.getNickname().isEmpty()) {
                member.setNickname(memberEditFormDto.getNickname());
                nicknameChanged = true;
            }
            if (memberEditFormDto.getAddress() != null) {
                member.setPostcode(memberEditFormDto.getAddress().getPostcode());
                member.setAddress(memberEditFormDto.getAddress().getAddress());
                member.setDetailAddress(memberEditFormDto.getAddress().getDetailAddress());
                member.setExtraAddress(memberEditFormDto.getAddress().getExtraAddress());

                String mergeAddress = memberEditFormDto.getAddress().getPostcode() + " " +
                        memberEditFormDto.getAddress().getAddress() + " " +
                        memberEditFormDto.getAddress().getDetailAddress() + " " +
                        memberEditFormDto.getAddress().getExtraAddress();
                member.setMerge_address(mergeAddress);
            }
            if (!memberEditFormDto.getNewPassword().isEmpty()) {
                if (!memberEditFormDto.getNewPassword().equals(memberEditFormDto.getConfirmPassword())) {
                    bindingResult.rejectValue("confirmPassword", "newPassword.mismatch", "비밀번호가 일치하지 않습니다.");
                    return;
                }
                if (passwordPatternIsValid(memberEditFormDto.getNewPassword())) {
                    member.setPassword(passwordEncoder.encode(memberEditFormDto.getNewPassword()));
                } else {
                    bindingResult.rejectValue("newPassword", "passwordPattern.invalid", "비밀번호는 8~16자, 숫자, 특수문자를 사용하세요.");
                    return;
                }
            }
            if (authentication instanceof AnonymousAuthenticationToken) {
                member.setModifiedBy("anonymousUser");
            } else {
                member.setModifiedBy(email);
            }

            memberRepository.save(member);

            if (nicknameChanged) {
                refreshUserSession(email);
            }
        } else {
            throw new IllegalArgumentException("회원 정보를 찾을 수 없습니다.");
        }
    }


    public void deactivateMember(String email, String password, Member member) {
        if (passwordEncoder.matches(password, member.getPassword())) {
            memberRepository.delete(member);
        } else {
            throw new IllegalArgumentException("패스워드가 일치하지 않습니다.");
        }
    }

    public void refreshUserSession(String email) {
        UserDetails userDetails = this.loadUserByUsername(email);
        UsernamePasswordAuthenticationToken newAuth =
                new UsernamePasswordAuthenticationToken(userDetails,
                        null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }

    // 패턴 검증 메서드
    private boolean passwordPatternIsValid(String password) {
        // 비밀번호는 8~16자이고, 숫자와 특수 문자를 포함해야 합니다.
        String passwordPattern = "^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-zA-Z]).{8,16}$";
        Pattern pattern = Pattern.compile(passwordPattern);
        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }

    private void validateDupulicateMember(Member member) {
        Member findMember = memberRepository.findByEmail(member.getEmail());
        if (findMember != null) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }

    }


    @Override
    public UserDetails loadUserByUsername(String email) throws
            UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email);

        if (member != null) {
            return new CustomUserDetails(member);
        } else {
            throw new UsernameNotFoundException("회원을 찾을 수 없습니다: " + email);

        }

    }


    public MemberInfoFormDto getUserInfo(Long id) {
        Optional<Member> optionalMember = memberRepository.findById(id);
        MemberInfoFormDto memberInfo = new MemberInfoFormDto();

        optionalMember.ifPresent(member -> {

            memberInfo.setId(member.getId());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedRegTime = member.getRegTime().format(formatter);
            String formattedUpdateTime = member.getUpdateTime().format(formatter);

            memberInfo.setReg_time(formattedRegTime);
            memberInfo.setUpdate_time(formattedUpdateTime);

            memberInfo.setAddress(member.getAddress());

            if(member.getAge() == 0) {
                setAge(member.getId());
            }

            memberInfo.setAge(member.getAge());


            memberInfo.setEmail(member.getEmail());
            memberInfo.setGender(member.getGender());
            memberInfo.setNickName(member.getNickname());
            memberInfo.setPoint(member.getPoint());

        });

        return memberInfo;
    }

    public void setAge(Long userId) {
        Optional<Member> optionalMember = memberRepository.findById(userId);

        optionalMember.ifPresent(member -> {

            String kakaoAge = member.getAge_range();

            if (kakaoAge.equals("0~9")) {
                member.setAge(0);
            } else if (kakaoAge.equals("20~29")) {
                member.setAge(20);
            } else if (kakaoAge.equals("30~39")) {
                member.setAge(30);
            } else if (kakaoAge.equals("40~49")) {
                member.setAge(40);
            } else if (kakaoAge.equals("50~59")) {
                member.setAge(50);
            } else if (kakaoAge.equals("60~69")) {
                member.setAge(60);
            }  else if (kakaoAge.equals("70~79")) {
                member.setAge(70);
            }  else if (kakaoAge.equals("80~89")) {
                member.setAge(80);
            }  else if (kakaoAge.equals("90~99")) {
                member.setAge(90);
            } else {
                member.setAge(100);
            }

        });
    }
}
