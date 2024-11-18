package homeTry.member.controller;

import homeTry.common.annotation.LoginMember;
import homeTry.member.dto.MemberDTO;
import homeTry.member.dto.request.ChangeNicknameRequest;
import homeTry.member.dto.response.MyPageResponse;
import homeTry.member.service.MemberService;
import homeTry.member.service.MemberWithdrawService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Member", description = "마이페이지 관련 API")
@RestController
@RequestMapping("/api/member/profile")
public class MemberController {

    private final MemberService memberService;
    private final MemberWithdrawService memberWithdrawService;

    @Autowired
    public MemberController(MemberService memberService,
            MemberWithdrawService memberWithdrawService) {
        this.memberService = memberService;
        this.memberWithdrawService = memberWithdrawService;
    }

    @GetMapping
    @Operation(summary = "회원 정보 불러오기", description = "회원 정보를 가져와서 MyPageResponse 객체를 담아서 응답한다")
    @ApiResponse(responseCode = "200", description = "회원 정보를 성공적으로 불러옴")
    public ResponseEntity<MyPageResponse> getMemberInfo(@LoginMember MemberDTO memberDTO) {
        return ResponseEntity.ok(memberService.getMemberInfo(memberDTO));
    }

    @PutMapping
    @Operation(summary = "닉네임 변경", description = "회원의 닉네임을 변경한다")
    @ApiResponse(responseCode = "200", description = "회원의 닉네임을 성공적으로 변경함")
    public ResponseEntity<Void> changeNickname(@LoginMember MemberDTO memberDTO,
            @RequestBody ChangeNicknameRequest changeNicknameRequest) {
        memberService.changeNickname(memberDTO.id(), changeNicknameRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    @Operation(summary = "회원 탈퇴", description = "회원을 탈퇴한다.")
    @ApiResponse(responseCode = "204", description = "회원을 성공적으로 탈퇴함")
    public ResponseEntity<Void> deleteMember(@LoginMember MemberDTO memberDTO) {
        memberWithdrawService.withdraw(memberDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
