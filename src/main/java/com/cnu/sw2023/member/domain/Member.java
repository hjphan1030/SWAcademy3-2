package com.cnu.sw2023.member.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private College college;

    @Builder
    public Member(String email, String password, College college) {
        this.email = email;
        this.password = password;
        this.college = college;
    }

    public Member() {

    }
}
