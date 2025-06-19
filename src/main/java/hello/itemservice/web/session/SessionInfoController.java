package hello.itemservice.web.session;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/session")
public class SessionInfoController {

    @GetMapping("/info")
    public String sessionInfo(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "Session does not exist.";
        }

        // 세션 기본 정보 출력
        log.info("=========== session info ===========");
        log.info("sessionId={}", session.getId());
        log.info("sessionMaxInactiveInterval={}", session.getMaxInactiveInterval());
        log.info("sessionCreationTime={}", new Date(session.getCreationTime()));
        log.info("sessionLastAccessedTime={}", new Date(session.getLastAccessedTime()));
        log.info("sessionisNew={}", session.isNew());

        // 세션이 가진 정보들 출력
        session.getAttributeNames()
                .asIterator()
                .forEachRemaining(sname -> log.info("[attr] session name={}, value={}", sname, session.getAttribute(sname)));

        return "Session OK.";
    }
}
