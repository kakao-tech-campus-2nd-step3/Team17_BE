package homeTry.member.service;


import homeTry.exerciseList.service.ExerciseHistoryService;
import homeTry.member.dto.MemberDTO;
import homeTry.member.dto.request.ChangeNicknameRequest;
import homeTry.member.dto.response.MyPageResponse;
import homeTry.member.exception.badRequestException.LoginFailedException;
import homeTry.member.exception.badRequestException.MemberNotFoundException;
import homeTry.member.exception.badRequestException.RegisterEmailConflictException;
import homeTry.member.model.entity.Member;
import homeTry.member.model.vo.Email;
import homeTry.member.model.vo.Nickname;
import homeTry.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Service
public class MemberService {

    private final ExerciseHistoryService exerciseHistoryService;
    private final MemberRepository memberRepository;

    public MemberService(ExerciseHistoryService exerciseHistoryService,
                         MemberRepository memberRepository) {
        this.exerciseHistoryService = exerciseHistoryService;
        this.memberRepository = memberRepository;
    }

    @Transactional(readOnly = true)
    public Long login(MemberDTO memberDTO) {
        return memberRepository.findByEmail(new Email(memberDTO.email())).orElseThrow(LoginFailedException::new).getId();
    }

    @Transactional
    public Long register(MemberDTO memberDTO) {
        Member member = memberDTO.toEntity();

        if (memberRepository.existsByEmail(new Email(memberDTO.email())))
            throw new RegisterEmailConflictException();

        return memberRepository.save(member).getId();
    }

    @Transactional(readOnly = true)
    public MemberDTO getMember(Long id) {
        return MemberDTO.from(getMemberEntity(id));
    }

    @Transactional(readOnly = true)
    public Member getMemberEntity(Long id) {
        return memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
    }

    @Transactional
    public void setMemeberAccessToken(Long id, String kakaoAccessToken) {
        Member member = getMemberEntity(id);
        member.setKakaoAccessToken(kakaoAccessToken);
    }

    @Transactional
    public void changeNickname(Long id, ChangeNicknameRequest changeNicknameRequest) {
        Member member = getMemberEntity(id);
        member.changeNickname(new Nickname(changeNicknameRequest.name()));
    }

    @Transactional
    public void incrementAttendanceDate(Long id){
        Member member = getMemberEntity(id);
        member.incrementAttendanceDate();
    }

    @Transactional(readOnly = true)
    public MyPageResponse getMemberInfo(MemberDTO memberDTO) {
        Long id = memberDTO.id();

        Long weeklyTotal = exerciseHistoryService.getWeeklyTotalExercise(id);
        Long monthlyTotal = exerciseHistoryService.getMonthlyTotalExercise(id);

        return new MyPageResponse(memberDTO.nickname(), memberDTO.email(),
                getMemberEntity(id).getExerciseAttendanceDate(), weeklyTotal, monthlyTotal);
    }
}
