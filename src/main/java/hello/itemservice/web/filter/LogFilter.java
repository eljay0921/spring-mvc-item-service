package hello.itemservice.web.filter;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.UUID;

@Slf4j
public class LogFilter implements Filter {

    /**
     * 필터 초기화 메서드, 서블릿 컨테이너가 생성될 때마다 호출된다.
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("[LogFilter] init");
        Filter.super.init(filterConfig);
    }

    /**
     * 필터 실행 메서드, 요청이 올 때마다 해당 메서드가 호출된다.
     * 원하는 필터의 기능을 이곳에 구현한다.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("[LogFilter] do");

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        String uuid = UUID.randomUUID().toString();

        try {
            log.info("[LogFilter] do: UUID-{}, URI-{}", uuid, requestURI);

            // (중요) chain -> 다음 단계(Filter or Servlet)로 넘어가도록 반드시 작성해줘야 한다
            chain.doFilter(request, response);
        } catch (Exception e) {
            throw e;
        } finally {
            log.info("[LogFilter] do: OK");
        }
    }

    /**
     * 필터 종료 메서드, 서블릿 컨테이너가 종료될 때 호출된다.
     */
    @Override
    public void destroy() {
        log.info("[LogFilter] destroy");
        Filter.super.destroy();
    }
}
