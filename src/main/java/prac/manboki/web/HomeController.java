package prac.manboki.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import prac.manboki.domain.member.Member;
import prac.manboki.domain.pedometer.Pedometer;
import prac.manboki.domain.pedometer.PedometerService;
import prac.manboki.web.session.SessionConst;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final PedometerService pedometerService;

    @RequestMapping("/v1/pedometer")
    public String homeLogin(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember, Model model) {

        if (loginMember == null) {
            return "main";
        }

        Optional<Pedometer> findPedometer = pedometerService.findByEmail(loginMember.getEmail());

        model.addAttribute("member", findPedometer);
        return "pedometer";
    }
}
