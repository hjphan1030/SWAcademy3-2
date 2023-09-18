package com.cnu.sw2023.email;


import lombok.Getter;

import javax.validation.constraints.Email;

@Getter
public class EmailConfirmDto {

    @Email
    private String email;
}
