package homeTry.member.service;

import homeTry.exception.clientException.BadRequestException;
import homeTry.exception.clientException.UserNotFoundException;
import homeTry.exception.serverException.InternalServerException;
import homeTry.member.dto.MemberDTO;
import homeTry.member.model.entity.Member;
import homeTry.member.model.vo.Email;
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
    public void login(MemberDTO memberDTO) throws RuntimeException {
        try {
            if (memberRepository.countByEmailAndPassword(new Email(memberDTO.email()), new Password(memberDTO.password())) < 1)
            { throw new UserNotFoundException("아이디 또는 비밀번호가 올바르지 않습니다."); }

            if (memberRepository.countByEmailAndPassword(new Email(memberDTO.email()), new Password(memberDTO.password())) > 1)
            { throw new InternalServerException("DB 무결성 오류"); }

            // 로그인 성공

        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException(e.getMessage());
        }
    }

    @Transactional
    public void register(MemberDTO memberDTO) throws RuntimeException {
        try {
            Member member = memberDTO.convertToMember(LocalDateTime.now()); //가입 날짜 정의
            memberRepository.save(member);
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
    public MemberDTO getMember(String email) throws RuntimeException {
        try {
            Email memberEmail = new Email(email);
            if (memberRepository.countByEmail(memberEmail) == 1) {
                return MemberDTO.convertToMemberDTO(memberRepository.findByEmail(memberEmail).get());
            }
            if (memberRepository.countByEmail(memberEmail) > 1) {
                throw new InternalServerException(email + " -> 데이터 무결성 위반");
            }
            throw new UserNotFoundException(email + "을(를) 가지는 유저를 찾을 수 없습니다.");
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(e.getMessage());
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException(e.getMessage());

        }
    }

    @Transactional
    public void setMemeberAccessToken(String email, String kakaoAccessToken) throws RuntimeException {
        try{
            Member member = memberRepository.findByEmail(new Email(email)).orElseThrow(()
                    -> new BadRequestException(email + "을(를) 가지는 유저를 찾을 수 없습니다."));

            member.setKakaoAccessToken(kakaoAccessToken);
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public String getMemberAccessToken(String email) throws RuntimeException {
        try{
            Member member = memberRepository.findByEmail(new Email(email)).orElseThrow(()
                    -> new BadRequestException(email + "을(를) 가지는 유저를 찾을 수 없습니다."));
            return member.getKakaoAccessToken();
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException(e.getMessage());
        }
    }

}
