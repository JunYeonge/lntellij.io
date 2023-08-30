package com.bed.config;

import com.bed.entity.Member;
import com.bed.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class MemberDataCleanupScheduler {
    @Autowired
    private MemberRepository memberRepository;

    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 실행
    public void cleanupDeactivatedMembers() {
        LocalDateTime threeMonthsAgo = LocalDateTime.now().minusMonths(0);
        List<Member> members = memberRepository.findAllByActiveIsFalseAndDeactivatedAtBefore(threeMonthsAgo);
        memberRepository.deleteAll(members);
    }
}
