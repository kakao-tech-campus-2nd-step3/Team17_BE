package homeTry.member.controller;

import homeTry.common.annotation.LoginMember;
import homeTry.member.dto.MemberDTO;
import homeTry.member.dto.request.ChangeNicknameRequest;
import homeTry.member.dto.response.MyPageResponse;
import homeTry.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/profile")
    public ResponseEntity<MyPageResponse> getMemberInfo(@LoginMember MemberDTO memberDTO) {
        return ResponseEntity.ok(memberService.getMemberInfo(memberDTO));
    }

    @PutMapping("/profile")
    public ResponseEntity<Void> changeNickname(@LoginMember MemberDTO memberDTO,
                                               @RequestBody ChangeNicknameRequest changeNicknameRequest) {
        memberService.changeNickname(memberDTO.id(), changeNicknameRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
