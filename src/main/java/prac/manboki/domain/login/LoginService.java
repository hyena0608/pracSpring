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
    public Member login(String email, String password) {
       return memberService.findByEmail(email)
               .filter(m -> m.getPassword().equals(password))
               .orElse(null);
    }
}
