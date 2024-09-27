package homeTry.auth;

import homeTry.annotation.LoginMember;
import homeTry.exception.clientException.BadRequestException;
import homeTry.exception.clientException.UserNotFoundException;
import homeTry.exception.serverException.InternalServerException;
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
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        try{
            String token = webRequest.getHeader("Authorization").substring(7);
            Long id = jwtAuth.extractId(token);
            return memberService.getMember(id);
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException("회원이 아닙니다.");
        } catch (NullPointerException e){
            throw new BadRequestException("토큰이 존재하지 않습니다.");
        } catch (Exception e) {
            throw new InternalServerException(e.getMessage());
        }
    }
}