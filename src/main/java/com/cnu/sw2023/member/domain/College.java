package com.cnu.sw2023.member.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum College {
    공학대학("공학대학"),
    자연과학대학("자연과학대학"),
    인문과학대학("인문과학대학"),
    예술대학("예술대학");

    private final String name;

    private College(String name) {
        this.name = name;
    }

    public static College fromString(String text) {
        for (College college : College.values()) {
            if (college.name.equalsIgnoreCase(text)) {
                return college;
            }
        }
        throw new IllegalArgumentException("유효한 단과대학을 입력하세요.");
    }
}