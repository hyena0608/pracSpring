package prac.manboki.web.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import prac.manboki.domain.member.Member;
import prac.manboki.domain.member.MemberService;

import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

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
