package homeTry.admin.service;

import homeTry.admin.dto.request.AdminCodeRequest;
import homeTry.admin.exception.badReqeustException.InvalidAdminCodeException;
import homeTry.member.service.MemberService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:application-secret.properties")
public class AdminPageService {

    private final MemberService memberService;

    public AdminPageService(MemberService memberService) {
        this.memberService = memberService;
    }

    @Value("${admin-code}")
    private String adminPromoteCode;

    public void promoteAdmin(AdminCodeRequest adminCodeRequest, Long memberId) {

        if (!isValidAdminCode(adminCodeRequest.adminCode()))
            throw new InvalidAdminCodeException();

        memberService.promoteToAdmin(memberId);
    }

    private boolean isValidAdminCode(String inputAdminCode) {
        return adminPromoteCode.equals(inputAdminCode);
    }
}
