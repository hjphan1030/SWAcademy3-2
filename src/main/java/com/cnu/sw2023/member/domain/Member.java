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


    @Builder
    public Member(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Member() {

    }
}
