package homeTry.member.service;


import homeTry.common.auth.kakaoAuth.dto.KakaoMemberInfoDTO;
import homeTry.common.auth.kakaoAuth.dto.KakaoMemberWithdrawDTO;
import homeTry.exerciseList.service.ExerciseHistoryService;
import homeTry.member.dto.MemberDTO;
import homeTry.member.dto.request.ChangeNicknameRequest;
import homeTry.member.dto.response.MyPageResponse;
import homeTry.member.exception.badRequestException.InactivatedMemberException;
import homeTry.member.exception.badRequestException.LoginFailedException;
import homeTry.member.exception.badRequestException.MemberNotFoundException;
import homeTry.member.exception.badRequestException.RegisterEmailConflictException;
import homeTry.member.model.entity.Member;
import homeTry.member.model.enums.Role;
import homeTry.member.model.vo.Email;
import homeTry.member.model.vo.Nickname;
import homeTry.member.repository.MemberRepository;
import homeTry.member.utils.RandomNicknameGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public MemberDTO login(KakaoMemberInfoDTO kakaoMemberInfoDTO) {
        Member member = memberRepository.findByEmail(new Email(kakaoMemberInfoDTO.email()))
                .orElseThrow(LoginFailedException::new);

        if(member.isInactive())
            throw new InactivatedMemberException();

        return MemberDTO.from(member);
    }

    @Transactional
    public MemberDTO register(KakaoMemberInfoDTO kakaoMemberInfoDTO) {

        MemberDTO memberDTO = new MemberDTO(kakaoMemberInfoDTO.email(),
                RandomNicknameGenerator.generateNickname(), Role.USER);

        Member member = memberDTO.toEntity();

        if (memberRepository.existsByEmail(new Email(memberDTO.email())))
            throw new RegisterEmailConflictException();

        memberRepository.save(member);

        setKakaoMemberId(member.getId(), kakaoMemberInfoDTO.kakaoMemberId());

        return MemberDTO.from(member);
    }

    @Transactional(readOnly = true)
    public MemberDTO getMember(Long id) {
        Member member = getMemberEntity(id);

        if(member.isInactive())
            throw new InactivatedMemberException();

        return MemberDTO.from(member);
    }

    @Transactional(readOnly = true)
    public Member getMemberEntity(Long id) {
        return memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
    }

    @Transactional
    public void setMemberAccessToken(Long id, String kakaoAccessToken) {
        Member member = getMemberEntity(id);
        member.setKakaoAccessToken(kakaoAccessToken);
    }

    @Transactional
    public void setKakaoMemberId(Long memberId, Long kakaoMemberId) {
        Member member = getMemberEntity(memberId);
        member.setKakaoMemberId(kakaoMemberId);
    }

    @Transactional
    public void changeNickname(Long id, ChangeNicknameRequest changeNicknameRequest) {
        Member member = getMemberEntity(id);
        member.changeNickname(new Nickname(changeNicknameRequest.name()));
    }

    @Transactional
    public void incrementAttendanceDate(Long id) {
        Member member = getMemberEntity(id);
        member.incrementAttendanceDate();
    }

    @Transactional(readOnly = true)
    public MyPageResponse getMemberInfo(MemberDTO memberDTO) {
        Member member = getMemberEntity(memberDTO.id());
        Long id = member.getId();

        Long weeklyTotal = exerciseHistoryService.getWeeklyTotalExercise(id);
        Long monthlyTotal = exerciseHistoryService.getMonthlyTotalExercise(id);

        return new MyPageResponse(member.getId(), member.getNickname(),
                member.getEmail(), member.getExerciseAttendanceDate(), weeklyTotal, monthlyTotal);
    }

    @Transactional
    public KakaoMemberWithdrawDTO withdrawMember(Long id) {
        Member member = getMemberEntity(id);
        KakaoMemberWithdrawDTO kakaoMemberWithdrawDTO = new KakaoMemberWithdrawDTO(
                member.getKakaoMemberId(), member.getKakaoAccessToken());
        deactivateMember(member);

        return kakaoMemberWithdrawDTO;
    }

    @Transactional
    public void promoteToAdmin(Long id) {
        Member member = getMemberEntity(id);
        member.promoteToAdmin();
    }

    @Transactional
    public void demoteToUser(Long id) {
        Member member = getMemberEntity(id);
        member.demoteToUser();
    }

    @Transactional(readOnly = true)
    public boolean isAdmin(Long id) {
        Member member = getMemberEntity(id);
        return member.isAdmin();
    }

    @Transactional(readOnly = true)
    public Role getRole(Long id) {
        Member member = getMemberEntity(id);
        return member.getRole();
    }

    private void deactivateMember(Member member) {
        member.revokeEmail();
        member.revokeNickname();
        member.revokeExerciseAttendanceDate();
        member.revokeKakaoMemberId();
        member.revokeKakaoAccessToken();
        member.demoteToUser();
        member.deactivate();
    }
}
