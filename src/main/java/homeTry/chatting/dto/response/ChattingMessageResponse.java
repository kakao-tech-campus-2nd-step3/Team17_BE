package homeTry.chatting.dto.response;

import homeTry.chatting.model.entity.Chatting;
import homeTry.member.model.entity.Member;
import java.time.LocalDateTime;

public record ChattingMessageResponse(Long chatId, Long memberId, String nickName, LocalDateTime chattedAt, String message) {
    public static ChattingMessageResponse from(Chatting chatting) {
        Member member = chatting.getTeamMemberMapping().getMember();
        return new ChattingMessageResponse(chatting.getId(), member.getId(),
                member.getNickname(), chatting.getCreatedAt(), chatting.getMessage());
    }
}