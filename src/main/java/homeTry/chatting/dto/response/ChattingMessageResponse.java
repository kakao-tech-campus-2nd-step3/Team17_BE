package homeTry.chatting.dto.response;

import homeTry.chatting.model.entity.Chatting;
import homeTry.member.model.entity.Member;

public record ChattingMessageResponse(Long chatId, Long memberId, String nickName, String message) {
    public static ChattingMessageResponse from(Chatting chatting) {
        Member member = chatting.getTeamMember().getMember();
        return new ChattingMessageResponse(chatting.getId(), member.getId(),
                member.getNickname(), chatting.getMessage());
    }
}