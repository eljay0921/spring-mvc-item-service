package hello.itemservice.web.argumentresolver;

import hello.itemservice.domain.member.Member;
import hello.itemservice.web.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        log.info("[supportsParameter] 실행");

        // Annotation 확인 -> HomeController의 homeV4(@Login Member member, ...)를 의미한다.
        boolean hasLoginAnnotations = parameter.hasParameterAnnotation(Login.class);
        boolean hasMemberType = Member.class.isAssignableFrom(parameter.getParameterType());

        // @Login 어노테이션이면서, Member 타입이라면 true
        return hasLoginAnnotations && hasMemberType;
    }

    // supportsParameter에서 true를 반환하면 실행된다.
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        log.info("[resolveArgument] 실행");

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        HttpSession session = request.getSession();
        if (session == null) {
            return null;
        }

        return session.getAttribute(SessionConst.LOGIN_MEMBER);
    }
}
