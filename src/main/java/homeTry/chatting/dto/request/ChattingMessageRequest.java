package homeTry.chatting.dto.request;

import homeTry.chatting.model.entity.Chatting;
import homeTry.team.model.entity.TeamMember;

// body에 message만 있는 이유는 클라이언트가 보낸 body 를 믿을 수 없기 때문이다.
// 대신 토큰을 이용해서 알아낼 수 있다.
public record ChattingMessageRequest(String message) {

    public Chatting toEntity(TeamMember teamMember){
        return new Chatting(teamMember, this.message);
    }

}
