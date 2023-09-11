package com.cnu.sw2023.member.DTO;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class JoinReqDto {

    @NotEmpty(message = "이메일은 필수항목입니다.")
    @Email
    private String email;

    @NotEmpty(message = "비밀번호는 필수항목입니다.")
    @Size(min = 3,max = 20,message = "비밀번호 길이는 3이상 20이하입니다")
    private String password;

    @NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
    private String password2;

    private String college;

}