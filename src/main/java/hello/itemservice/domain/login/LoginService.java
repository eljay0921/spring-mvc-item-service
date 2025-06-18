package hello.itemservice.domain.login;

import hello.itemservice.domain.member.Member;
import hello.itemservice.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    /**
     * @param loginId 로그인 ID
     * @param password 비밀번호
     * @return null이면 로그인 실패
     */
    public Member login(String loginId, String password) {
        Optional<Member> foundMember = memberRepository.findByLoginId(loginId);

        // as-is
        /*
        Member member = foundMember.get();
        if (member.getPassword().equals(password)) {
            return member;
        } else {
            return null;
        }
        */

        // to-be
        return foundMember
                .filter(member -> member.getPassword().equals(password))
                .orElse(null);
    }

}
