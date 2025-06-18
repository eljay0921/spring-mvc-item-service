package hello.itemservice.web.session;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {

    public static final String SESSION_COOKIE_NAME = "mySessionId";
    private final Map<String, Object> sessionStore = new ConcurrentHashMap<>();   // 동시성 고려

    /**
     * 세션 생성
     * @param obj
     * @param response
     */
    public void createSession(Object obj, HttpServletResponse response) {
        // 세션 ID 생성
        String sessionId = UUID.randomUUID().toString();
        sessionStore.put(sessionId, obj);

        // 쿠키 생성 - 전달
        Cookie sessionCookie = new Cookie(SESSION_COOKIE_NAME, sessionId);
        response.addCookie(sessionCookie);
    }

    /**
     * 세션 찾기
     * @param request
     * @return
     */
    public Object getSession(HttpServletRequest request) {
        Cookie cookie = findCookie(request, SESSION_COOKIE_NAME);
        if (cookie == null) {
            return null;
        }

        return sessionStore.get(cookie.getValue());
    }

    /**
     * 세션 만료
     * @param request
     */
    public void expireSession(HttpServletRequest request) {
        Cookie cookie = findCookie(request, SESSION_COOKIE_NAME);
        if (cookie != null) {
            sessionStore.remove(cookie.getValue());
        }
    }

    private Cookie findCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }

        return Arrays.stream(cookies)
                .filter(c -> c.getName().equals(cookieName))
                .findFirst()
                .orElse(null);
    }
}
