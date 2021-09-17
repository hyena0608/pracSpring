package prac.manboki.web.member;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import prac.manboki.controller.KakaoProfile;
import prac.manboki.controller.OAuthToken;
import prac.manboki.domain.member.Member;
import prac.manboki.domain.member.MemberService;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;


    @GetMapping("/auth/kakao/callback")
    public @ResponseBody String kakaoCallback(String code) {

        RestTemplate rt = new RestTemplate();

        // HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpBody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "1ff9b00bdf52d7b985ab36ef787b59fe");
        params.add("redirect_uri", "http://localhost:8080/auth/kakao/callback");
        params.add("code", code);

        // HttpHeader HttpBody
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, headers);

        // Http 요청 - Post - response 변수 응답
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oauthToken = null;
        try {
            oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        }catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        System.out.println("카카오 엑세스 토큰 : " + oauthToken.getAccess_token());

        RestTemplate rt2 = new RestTemplate();

        // HttpHeader 오브젝트 생성
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer " + oauthToken.getAccess_token());
        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpHeader HttpBody
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 =
                new HttpEntity<>(headers2);

        // Http 요청 - Post - response 변수 응답
        ResponseEntity<String> response2 = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest2,
                String.class
        );
        System.out.println(response2.getBody());

        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfile kakaoProfile = null;
        try {
            kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        }catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        System.out.println("카카오 아이디(번호) = " + kakaoProfile.getId());
        System.out.println("카카오 이메일 = " + kakaoProfile.getKakao_account().getEmail());

        System.out.println("만보기서버 유저네임 : " + kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId());
        System.out.println("만보기서버 이메일 : " + kakaoProfile.getKakao_account().getEmail());
        UUID garbagePassword = UUID.randomUUID();
        System.out.println("만보기서버 패스워드 : " + garbagePassword);

        Member member1 = new Member(
                garbagePassword.toString(),
                kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId(),
                kakaoProfile.getKakao_account().getEmail()
        );

        if (memberService.findByLoginId(kakaoProfile.getKakao_account().getEmail()) == null) {
            memberService.join(member1);
        }

//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(member1.getLoginId(), member1.getPassword()));
//        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "회원가입 완료";
    }

    @GetMapping("/add")
    public String addForm(@ModelAttribute("member") Member member) {
        return "members/loginForm";
    }

    @PostMapping("/add")
    public CreateMemberResponse joinMember(@RequestBody @Valid CreateMemberRequest request) {
        Member member = new Member(request.getPassword(), request.getName(), request.getEmail());
        Long id = memberService.join(member);

        return new CreateMemberResponse(id);
    }

    @Data
    static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }

    @Data
    @AllArgsConstructor
    static class CreateMemberRequest {
        private String password;
        private String name;
        private String nickname;
        private String email;
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String name;
    }
}
