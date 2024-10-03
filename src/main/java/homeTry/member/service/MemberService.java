package homeTry.member.service;

import homeTry.exception.clientException.BadRequestException;
import homeTry.exception.clientException.UserNotFoundException;
import homeTry.exception.serverException.InternalServerException;
import homeTry.member.dto.ChangeNicknameDTO;
import homeTry.member.dto.MemberDTO;
import homeTry.member.model.entity.Member;
import homeTry.member.model.vo.Email;
import homeTry.member.model.vo.Nickname;
import homeTry.member.model.vo.Password;
import homeTry.member.repository.MemberRepository;
import java.time.LocalDateTime;
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
            { throw new UserNotFoundException("아이디 또는 비밀번호가 올바르지 않습니다."); }

            if (memberRepository.countByEmailAndPassword(new Email(memberDTO.email()), new Password(memberDTO.password())) > 1)
            { throw new InternalServerException("DB 무결성 오류"); }

            // 로그인 성공
            return memberRepository.findByEmailAndPassword(new Email(memberDTO.email()), new Password(memberDTO.password()))
                    .get()
                    .getId();
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException(e.getMessage());
        }
    }

    @Transactional
    public Long register(MemberDTO memberDTO) throws RuntimeException {
        try {
            Member member = memberDTO.convertToMember(LocalDateTime.now()); //가입 날짜 정의
            return memberRepository.save(member).getId();
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("이미 있는 이메일입니다.");
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(e.getMessage());
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public MemberDTO getMember(Long id) throws RuntimeException {
        try {
            return MemberDTO.convertToMemberDTO(getMemberEntity(id));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(e.getMessage());
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public Member getMemberEntity(Long id) throws RuntimeException {
        try {
            return memberRepository.findById(id).orElseThrow(()
                    -> new UserNotFoundException("유저를 찾을 수 없습니다."));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(e.getMessage());
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException(e.getMessage());
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
            throw new InternalServerException(e.getMessage());
        }
    }

    @Transactional
    public void changeNickname(Long id, ChangeNicknameDTO changeNicknameDTO)
            throws RuntimeException {
        try{
            Member member = getMemberEntity(id);
            member.changeNickname(new Nickname(changeNicknameDTO.name()));
        } catch (BadRequestException | IllegalArgumentException e){
            throw new BadRequestException(e.getMessage());
        } catch (Exception e) {
            throw new InternalServerException(e.getMessage());
        }
    }
}
