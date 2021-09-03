package prac.manboki.controller;

import lombok.AllArgsConstructor;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
public class MemberForm {

    @NotEmpty(message = "회원 아이디를 입력해주세요.")
    private String id;
    private String password;
    private String name;
}
