package com.cnu.sw2023.member.DTO;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
@RequiredArgsConstructor
public class MemberLoginDto {
    String email;
    String password;
}
