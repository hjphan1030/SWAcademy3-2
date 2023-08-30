package com.cnu.sw2023.member.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberJoinDto { // 일단은 다 스트링 타입 으로 받자 ..

    String Email;
    String password1;
    String password2;
    String college;
    String department;
    String university;
    String studentNum;
}
