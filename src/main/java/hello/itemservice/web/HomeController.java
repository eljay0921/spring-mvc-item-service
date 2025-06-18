package hello.itemservice.web;

import hello.itemservice.domain.member.Member;
import hello.itemservice.domain.member.MemberRepository;
import hello.itemservice.web.session.SessionManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;
    private final SessionManager sessionManager;

    // (as-is)
    // @GetMapping("/")
    public String home(@CookieValue(name = "memberId", required = false) Long memberId, Model model) {
        if (memberId == null) {
            return "home";
        }

        Member member = memberRepository.findById(memberId);
        if (member == null) {
            return "home";
        }

        model.addAttribute("member", member);
        return "loginHome";
    }

    // (to-be)
    @GetMapping("/")
    public String homeV2(HttpServletRequest request, Model model) {

        // 세션을 먼저 조회
        Member sessionMember = (Member)sessionManager.getSession(request);
        if (sessionMember == null) {
            return "home";
        }

        Member foundMember = memberRepository.findById(sessionMember.getId());
        if (foundMember == null) {
            return "home";
        }

        model.addAttribute("member", foundMember);
        return "loginHome";
    }
}
