package homeTry.member.service;


import homeTry.exception.BadRequestException;
import homeTry.member.dto.ChangeNicknameDTO;
import homeTry.member.dto.MemberDTO;
import homeTry.member.exception.badRequestException.BadArgumentException;
import homeTry.member.exception.badRequestException.LoginFailedException;
import homeTry.member.exception.badRequestException.MemberNotFoundException;
import homeTry.member.exception.badRequestException.RegisterEmailConflictException;
import homeTry.member.exception.internalServerException.UniqueKeyViolatonException;
import homeTry.member.exception.internalServerException.UnknownFatalException;
import homeTry.member.model.entity.Member;
import homeTry.member.model.vo.Email;
import homeTry.member.model.vo.Nickname;
import homeTry.member.model.vo.Password;
import homeTry.member.repository.MemberRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) { this.memberRepository = memberRepository; }

    @Transactional(readOnly = true)
    public Long login(MemberDTO memberDTO) throws RuntimeException {
        try {
            if (memberRepository.countByEmailAndPassword(new Email(memberDTO.email()), new Password(memberDTO.password())) < 1)
            { throw new LoginFailedException(); }

            if (memberRepository.countByEmailAndPassword(new Email(memberDTO.email()), new Password(memberDTO.password())) > 1)
            { throw new UniqueKeyViolatonException(); }

            // 로그인 성공
            return memberRepository.findByEmailAndPassword(new Email(memberDTO.email()), new Password(memberDTO.password()))
                    .get()
                    .getId();
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new UnknownFatalException(e.getMessage());
        }
    }

    @Transactional
    public Long register(MemberDTO memberDTO) throws RuntimeException {
        try {
            Member member = memberDTO.convertToMember();
            return memberRepository.save(member).getId();
        } catch (DataIntegrityViolationException e) {
            throw new RegisterEmailConflictException();
        } catch (IllegalArgumentException e) {
            throw new BadArgumentException(e.getMessage());
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new UnknownFatalException(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public MemberDTO getMember(Long id) throws RuntimeException {
        try {
            return MemberDTO.convertToMemberDTO(getMemberEntity(id));
        } catch (IllegalArgumentException e) {
            throw new BadArgumentException(e.getMessage());
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new UnknownFatalException(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public Member getMemberEntity(Long id) throws RuntimeException {
        try {
            return memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
        } catch (IllegalArgumentException e) {
            throw new BadArgumentException(e.getMessage());
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new UnknownFatalException(e.getMessage());
        }
    }

    @Transactional
    public void setMemeberAccessToken(Long id, String kakaoAccessToken) throws RuntimeException {
        try{
            Member member = getMemberEntity(id);
            member.setKakaoAccessToken(kakaoAccessToken);
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new UnknownFatalException(e.getMessage());
        }
    }

    @Transactional
    public void changeNickname(Long id, ChangeNicknameDTO changeNicknameDTO) throws RuntimeException {
        try{
            Member member = getMemberEntity(id);
            member.changeNickname(new Nickname(changeNicknameDTO.name()));
        } catch (IllegalArgumentException e){
            throw new BadArgumentException(e.getMessage());
        } catch (BadRequestException e) {
            throw e;
        }
        catch (Exception e) {
            throw new UnknownFatalException(e.getMessage());
        }
    }
}
