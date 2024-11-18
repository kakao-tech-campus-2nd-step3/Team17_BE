package homeTry.common.auth;

import homeTry.common.annotation.LoginMember;
import homeTry.common.auth.exception.badRequestException.InvalidTokenException;
import homeTry.common.auth.exception.internalServerException.HomeTryServerException;
import homeTry.common.auth.jwt.JwtAuth;
import homeTry.common.exception.BadRequestException;
import homeTry.member.service.MemberService;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

    private final MemberService memberService;
    private final JwtAuth jwtAuth;

    public LoginMemberArgumentResolver(MemberService memberService, JwtAuth jwtAuth) {
        this.memberService = memberService;
        this.jwtAuth = jwtAuth;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(LoginMember.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        try {
            String token = webRequest.getHeader("Authorization").substring(7);
            Long id = jwtAuth.extractId(token);
            return memberService.getMember(id);
        } catch (BadRequestException e) {
            throw e;
        } catch (NullPointerException e) {
            throw new InvalidTokenException();
        } catch (Exception e) {
            throw new HomeTryServerException();
        }
    }
}