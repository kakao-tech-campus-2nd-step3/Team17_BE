package homeTry.member.controller;

import homeTry.annotation.LoginMember;
import homeTry.exerciseList.service.ExerciseService;
import homeTry.member.dto.ChangeNicknameDTO;
import homeTry.member.dto.MemberDTO;
import homeTry.member.dto.MyPageDTO;
import homeTry.member.service.MemberService;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/member")
public class MemberController {

    private final ExerciseService exerciseService;
    private final MemberService memberService;

    @Autowired
    public MemberController(ExerciseService exerciseService, MemberService memberService) {
        this.exerciseService = exerciseService;
        this.memberService = memberService;
    }

    @GetMapping("/profile")
    public ResponseEntity<MyPageDTO> getMemberInfo(@LoginMember MemberDTO memberDTO) {
        Long id = memberDTO.id()

        Duration weeklyTotal = exerciseService.getWeeklyTotalExercise(id);
        Duration monthlyTotal = exerciseService.getMonthlyTotalExercise(id);

        MyPageDTO myPageDTO = new MyPageDTO(memberDTO.nickname(), memberDTO.email(), weeklyTotal, monthlyTotal);

        return ResponseEntity.ok(myPageDTO);
    }

    @PutMapping("/profile")
    public ResponseEntity<Void> changeNickname(@LoginMember MemberDTO memberDTO,
            @RequestBody ChangeNicknameDTO changeNicknameDTO) {
        memberService.changeNickname(memberDTO.id(), changeNicknameDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
