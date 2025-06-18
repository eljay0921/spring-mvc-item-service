package hello.itemservice.web.login;

import hello.itemservice.domain.login.LoginService;
import hello.itemservice.domain.member.Member;
import hello.itemservice.web.SessionConst;
import hello.itemservice.web.session.SessionManager;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final SessionManager sessionManager;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm loginForm) {
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginForm") LoginForm loginForm, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            return "login/loginForm";
        }

        Member loginMember = loginService.login(loginForm.getLoginId(), loginForm.getPassword());
        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        // 로그인 처리 TODO

        // (as-is) 쿠키 방식
        /*
        Cookie loginCookie = new Cookie("memberId", String.valueOf(loginMember.getId()));
        response.addCookie(loginCookie);
        */

        // (to-be) (커스텀) 세션 방식
        // sessionManager.createSession(loginMember, response);

        // (to-be .v2) Servlet에서 제공하는 HttpSession 방식
        HttpSession session = request.getSession(); // 존재하면 해당 세션, 없으면 신규 세션 반환 (default=true / false면 null 반환)
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);   // 세션에 로그인 회원 정보 저장

        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {

        // (as-is) 쿠키 방식
        /*
        Cookie loginCookie = new Cookie("memberId", null);
        loginCookie.setMaxAge(0);   // 만료(로그아웃)
        response.addCookie(loginCookie);
        */

        // (to-be) (커스텀) 세션 방식
        // sessionManager.expireSession(request);

        // (to-be .v2) Servlet에서 제공하는 HttpSession 방식
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();   // session 데이터를 모두 제거한다.
        }

        return "redirect:/";    // home으로
    }
}
