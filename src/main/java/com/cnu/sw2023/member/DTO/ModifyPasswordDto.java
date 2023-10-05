package com.cnu.sw2023.member.DTO;

import lombok.Getter;

@Getter
public class ModifyPasswordDto {
    String nowPassword;
    String newPassword1;
    String newPassword2;
}
