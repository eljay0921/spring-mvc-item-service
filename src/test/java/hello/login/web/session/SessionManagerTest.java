package hello.login.web.session;

import hello.itemservice.domain.member.Member;
import hello.itemservice.web.session.SessionManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class SessionManagerTest {

    private SessionManager sessionManager = new SessionManager();

    @Test
    void sessionTest() {

        // HttpServletResponse는 인터페이스지만, Spring이 Mock- 클래스를 제공해준다
        MockHttpServletResponse response = new MockHttpServletResponse();

        // 세션 생성
        Member member = new Member();
        member.setLoginId("testID");
        member.setName("testName");
        sessionManager.createSession(member, response);

        // 응답 쿠키 저장
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setCookies(response.getCookies());

        // (test) 응답 쿠키 확인
        Assertions.assertThat(response.getCookies()).isNotNull();

        // (test) 세션 조회 확인
        Object session1 = sessionManager.getSession(request);
        Assertions.assertThat(session1).isEqualTo(member);

        // (test) 세션 만료 확인
        sessionManager.expireSession(request);
        Object session2 = sessionManager.getSession(request);
        Assertions.assertThat(session2).isNull();
    }
}
