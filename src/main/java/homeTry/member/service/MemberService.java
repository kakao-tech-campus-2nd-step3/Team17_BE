package homeTry.member.service;


import homeTry.exerciseList.service.ExerciseHistoryService;
import homeTry.member.dto.ChangeNicknameDTO;
import homeTry.member.dto.MemberDTO;
import homeTry.member.dto.MyPageDTO;
import homeTry.member.exception.badRequestException.LoginFailedException;
import homeTry.member.exception.badRequestException.MemberNotFoundException;
import homeTry.member.exception.badRequestException.RegisterEmailConflictException;
import homeTry.member.exception.internalServerException.UniqueKeyViolatonException;
import homeTry.member.model.entity.Member;
import homeTry.member.model.vo.Email;
import homeTry.member.model.vo.Nickname;
import homeTry.member.repository.MemberRepository;
import java.time.Duration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {
    private final ExerciseHistoryService exerciseHistoryService;
    private final MemberRepository memberRepository;

    public MemberService(ExerciseHistoryService exerciseHistoryService, MemberRepository memberRepository) {
        this.exerciseHistoryService = exerciseHistoryService;
        this.memberRepository = memberRepository; }

    @Transactional(readOnly = true)
    public Long login(MemberDTO memberDTO) {
        Long countByEmail = memberRepository.countByEmail(new Email(memberDTO.email()));

        if (countByEmail < 1) { throw new LoginFailedException(); }
        if (countByEmail > 1) { throw new UniqueKeyViolatonException(); }

        // 로그인 성공
        return memberRepository.findByEmail(new Email(memberDTO.email())).get().getId();
    }

    @Transactional
    public Long register(MemberDTO memberDTO) {
        Member member = memberDTO.convertToMember();

        if(memberRepository.countByEmail(new Email(memberDTO.email())) > 0)
            throw new RegisterEmailConflictException();

        return memberRepository.save(member).getId();
    }

    @Transactional(readOnly = true)
    public MemberDTO getMember(Long id) {
        return MemberDTO.convertToMemberDTO(getMemberEntity(id));
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
    public void changeNickname(Long id, ChangeNicknameDTO changeNicknameDTO) {
        Member member = getMemberEntity(id);
        member.changeNickname(new Nickname(changeNicknameDTO.name()));
    }

    @Transactional(readOnly = true)
    public MyPageDTO getMemberInfo(MemberDTO memberDTO) {
        Long id = memberDTO.id();
        Duration weeklyTotal = exerciseHistoryService.getWeeklyTotalExercise(id);
        Duration monthlyTotal = exerciseHistoryService.getMonthlyTotalExercise(id);

        return new MyPageDTO(memberDTO.nickname(), memberDTO.email(), weeklyTotal, monthlyTotal);
    }
}
