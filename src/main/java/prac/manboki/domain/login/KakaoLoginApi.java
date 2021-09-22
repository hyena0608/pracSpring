package prac.manboki.domain.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import prac.manboki.domain.login.OAuthToken;

import java.util.HashMap;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.servlet.http.HttpServletRequest;

public class KakaoLoginApi {

    /**
     * 카카오 토큰 받기
     */
    public String getAccessToken(String code) {

        String accessToken = "";
        String grant_type = "authorization_code";
        String client_id = "1ff9b00bdf52d7b985ab36ef787b59fe";
        String redirect_uri = "http://localhost:8080/auth/kakao/callback";

        try {
            RestTemplate rt = new RestTemplate();

            // HttpHeader 오브젝트 생성
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

            // HttpBody 오브젝트 생성
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("grant_type", grant_type);
            params.add("client_id", client_id);
            params.add("redirect_uri", redirect_uri);
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

            oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);

            accessToken = oauthToken.getAccess_token();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return accessToken;
    }

    /**
     * 받은 토큰으로
     * 이름, 이메일 받오기
     */
    public HashMap<String, Object> getUserInfo(String accessToken, HttpServletRequest request) {
        HashMap<String, Object> userInfo = new HashMap<String, Object>();

        try {
            RestTemplate rt = new RestTemplate();

            // HttpHeaders 오브젝트 생성
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + accessToken);
            headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

            // HttpHeader HttpBody
            HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers);

            // Http 요청 - Post - response 변수 응답
            ResponseEntity<String> response = rt.exchange(
                    "https://kapi.kakao.com/v2/user/me",
                    HttpMethod.POST,
                    kakaoProfileRequest,
                    String.class
            );

            JsonElement element = JsonParser.parseString(response.getBody());

            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            JsonObject kakaoAccount = element.getAsJsonObject().get("kakao_account").getAsJsonObject();


            String nickname = properties.getAsJsonObject().get("nickname").getAsString();
            String email = kakaoAccount.getAsJsonObject().get("email").getAsString();

            userInfo.put("nickname", nickname);
            userInfo.put("email", email);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return userInfo;
    }
}
