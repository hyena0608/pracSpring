package prac.manboki.web.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import prac.manboki.domain.login.KakaoLoginApi;
import prac.manboki.domain.login.LoginService;
import prac.manboki.domain.member.Member;
import prac.manboki.web.session.SessionConst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@Slf4j
public class KakaoLoginController {

    private final LoginService loginService;
    KakaoLoginApi kakaoLoginApi = new KakaoLoginApi();


    @GetMapping("/auth/kakao/callback")
    public String login(@RequestParam("code") String code, HttpServletRequest request) {
        // 1번 인증코드 요청 전달
        String accessToken = kakaoLoginApi.getAccessToken(code);
        // 2번 인증코드로 토큰 전달
        HashMap<String, Object> userInfo = kakaoLoginApi.getUserInfo(accessToken, request);
        UUID garbagePassword = UUID.randomUUID();
        Member loginMember = loginService.autoJoin(
                garbagePassword + "",
                userInfo.get("nickname") + "",
                userInfo.get("email") + ""
        );

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

        return "pedometer";
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
}
