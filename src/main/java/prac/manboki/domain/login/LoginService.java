package prac.manboki.domain.login;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import prac.manboki.domain.member.Member;
import prac.manboki.domain.member.MemberService;
import prac.manboki.domain.pedometer.Pedometer;
import prac.manboki.domain.pedometer.PedometerService;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberService memberService;
    private final PedometerService pedometerService;

    /**
     * @return null이면 로그인 실패
     */
    public Member login(String email, String password) {
        return memberService.findByEmail(email)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }

    public Member autoLoginWithJoin(String password, String nickname, String email) {
        Member member = createMember(password, nickname, email);
        Pedometer pedometer = createPedometer(member, 0, 0);
        if (memberService.findByEmail(email).isEmpty()) {
            memberService.join(member);
            pedometerService.join(pedometer);
            return member;
        } else if (memberService.findByEmail(email).isPresent()) {
            return login(email, password);
        }
        return null;
    }

    private Member createMember(String password, String nickname, String email) {
        return new Member(password, nickname, email);
    }

    private Pedometer createPedometer(Member member, int todaySteps, int totalSteps) {
        return new Pedometer(member, todaySteps, totalSteps);
    }
}
