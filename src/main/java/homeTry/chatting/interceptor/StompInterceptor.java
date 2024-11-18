package homeTry.chatting.interceptor;

import homeTry.chatting.exception.badRequestException.InactivatedMemberWithValidTokenException;
import homeTry.chatting.exception.badRequestException.InvalidChattingTokenException;
import homeTry.chatting.exception.badRequestException.NoSuchMemberInDbWithValidTokenException;
import homeTry.common.auth.jwt.JwtAuth;
import homeTry.member.dto.MemberDTO;
import homeTry.member.exception.badRequestException.InactivatedMemberException;
import homeTry.member.exception.badRequestException.MemberNotFoundException;
import homeTry.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
public class StompInterceptor implements ChannelInterceptor {

    private final JwtAuth jwtAuth;
    private final MemberService memberService;

    @Autowired
    public StompInterceptor(JwtAuth jwtAuth, MemberService memberService) {
        this.jwtAuth = jwtAuth;
        this.memberService = memberService;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        //Stomp 메세지 intercept
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        handleConnectCommand(accessor);
        //추후 다른 케이스 있다면 추가하기

        return message;
    }

    private void handleConnectCommand(StompHeaderAccessor accessor) {
        if (accessor.getCommand() == StompCommand.CONNECT) {
            MemberDTO memberDTO;

            try {
                String token = String.valueOf(accessor.getNativeHeader("Authorization").getFirst())
                        .substring(7); // Expect after B e a r e r _

                memberDTO = memberService.getMember(jwtAuth.extractId(token));
            } catch (MemberNotFoundException e) {
                throw new NoSuchMemberInDbWithValidTokenException();
            } catch (InactivatedMemberException e) {
                throw new InactivatedMemberWithValidTokenException();
            } catch (Exception e) {
                throw new InvalidChattingTokenException();
            }

            //세션에 저장
            accessor.getSessionAttributes().put("member", memberDTO);
        }
    }
}
