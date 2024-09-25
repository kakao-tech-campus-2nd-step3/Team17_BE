package homeTry.member.controller;

import homeTry.annotation.LoginMember;
import homeTry.exerciseList.service.ExerciseListService;
import homeTry.member.dto.MemberDTO;
import homeTry.member.dto.MyPageDTO;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/member")
public class MemberController {

    private final ExerciseListService exerciseListService;

    @Autowired
    public MemberController(ExerciseListService exerciseListService) {
        this.exerciseListService = exerciseListService;
    }

    @GetMapping("/profile")
    public ResponseEntity<MyPageDTO> getMemberInfo(@LoginMember MemberDTO memberDTO) {
        String email = memberDTO.email();

        Duration weeklyTotal = exerciseListService.getWeeklyTotalExercise(email);
        Duration monthlyTotal = exerciseListService.getMonthlyTotalExercise(email);

        MyPageDTO myPageDTO = new MyPageDTO(memberDTO.nickname(), email, weeklyTotal, monthlyTotal);

        return ResponseEntity.ok(myPageDTO);
    }
}
