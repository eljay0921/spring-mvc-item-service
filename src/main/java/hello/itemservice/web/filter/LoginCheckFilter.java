package hello.itemservice.web.filter;

import hello.itemservice.web.SessionConst;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

@Slf4j
public class LoginCheckFilter implements Filter {

    /**
     * 로그인 확인 필터를 통과시키기 위한 whitelist
     * 로그인까지는 진입을 해야 로그인이 가능하기 때문..
     */
    private static final String[] whiteList = {"/", "/members/add", "/login", "/logout", "/css/*"};

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("[LoginCheckFilter] init");
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {
            log.info("[LoginCheckFilter] 시작");

            if (isWhiteList(requestURI) == false) {
                log.info("[LoginCheckFilter] 인증 체크 시작");
                HttpSession session = httpRequest.getSession(false);

                if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
                    log.info("[LoginCheckFilter] 미인증 사용자");
                    httpResponse.sendRedirect("/login?redirectURL=" + requestURI);  // 사용자가 로그인 했을 때, 다시 원래 요청했던 uri로 보내주기 위함
                    return;
                }
            }

            log.info("[LoginCheckFilter] whitelist / 인증 OK");
            chain.doFilter(request, response);

        } catch (Exception e) {
            throw e;
        } finally {
            log.info("[LoginCheckFilter] 종료");
        }
    }

    private boolean isWhiteList(String uri) {
        return PatternMatchUtils.simpleMatch(whiteList, uri);
    }

    @Override
    public void destroy() {
        log.info("[LoginCheckFilter] destroy");
        Filter.super.destroy();
    }
}
