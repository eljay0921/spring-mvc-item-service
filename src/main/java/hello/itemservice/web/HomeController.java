package hello.itemservice.web;

import hello.itemservice.domain.member.Member;
import hello.itemservice.domain.member.MemberRepository;
import hello.itemservice.web.session.SessionManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
        // (to-be) custom session
        /*
        Member sessionMember = (Member)sessionManager.getSession(request);
        if (sessionMember == null) {
            return "home";
        }
        */

        // (to-be) Servlet 제공 HttpSession
        HttpSession session = request.getSession(false);// 로그인을 하지 않은 경우가 있으니 false로 진행
        if (session == null) {
            return "home";
        }

        // 강의에선 분량 문제로 그냥 넘어가는 듯 하지만, 안전한 캐스팅이 필요해 보인다.
        // Member sessionMember = (Member)session.getAttribute(SessionConst.LOGIN_MEMBER); // (X)

        Object sessionAttr = session.getAttribute(SessionConst.LOGIN_MEMBER);
        // if (sessionAttr != null && sessionAttr instanceof Member) {     // Java 11 이하
        if (sessionAttr instanceof Member sessionMember) {  // Java 16 이상 (패턴 매칭을 통해 형번환과 선언이 한 번에!)

            // 강의에선 없는 부분이긴 한데, DB에 존재하는 회원인지 확인해야 하지 않을까 싶어 추가
            Member foundMember = memberRepository.findById(sessionMember.getId());
            if (foundMember == null) {
                return "home";
            }

            model.addAttribute("member", foundMember);
            return "loginHome";
        } else {
            return "home";
        }
    }
}
