package prac.manboki.domain.login;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import prac.manboki.domain.member.Member;
import prac.manboki.domain.member.MemberService;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberService memberService;

    /**
     * @return null이면 로그인 실패
     */
    public Member login(String loginId, String password) {
       return memberService.findByLoginId(loginId)
               .filter(m -> m.getPassword().equals(password))
               .orElse(null);
    }
}
