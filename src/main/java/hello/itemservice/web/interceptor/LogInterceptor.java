package hello.itemservice.web.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    private static final String LOG_ID = "logId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        String uuid = UUID.randomUUID().toString(); // uuid를 afterCompletion으로 넘길 방법?
        request.setAttribute(LOG_ID, uuid);    // solution...(?)

        // @RequestMapping -> HandlerMethod
        // 정적 리소스 -> ResourceHttpRequestHandler
        /*
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler; // 호출할 컨트롤러 메서드의 모든 정보가 포함되어 있다.
        }
        // 참고용 소스
        */
        log.info("preHandle(req) :: [{}][{}][{}]", uuid, requestURI, handler);

        // false로 리턴하면, 요청이 종료된다. (preHandle()에서 끝나면 핸들러 어댑터까지 넘어가지도 않음)
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
         log.info("postHandle :: [{}]", modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String requestURI = request.getRequestURI();
        String uuid = request.getAttribute(LOG_ID).toString();
        log.info("afterCompletion(res) :: [{}][{}][{}]", uuid, requestURI, handler);

        if (ex != null) {
            log.error("afterCompletion ERROR ! - ", ex);
        }
    }
}
