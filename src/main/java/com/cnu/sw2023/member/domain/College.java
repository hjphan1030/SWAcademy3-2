package com.cnu.sw2023.member.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum College {
    인문대학("인문대학"),
    사회과학대학("사회과학대학"),
    자연과학대학("자연과학대학"),
    경상대학("경상대학"),
    공과대학("공과대학"),
    농업생명과학대학("농업생명과학대학"),
    약학대학("약학대학"),
    의과대학("의과대학"),
    생활과학대학("생활과학대학"),
    예술대학("예술대학"),
    수의과대학("수의과대학"),
    사범대학("사범대학"),
    간호대학("간호대학"),
    생명시스템과학대학("생명시스템과학대학"),
    자유전공학부("자유전공학부"),
    국가안보융합학부("국가안보융합학부"),
    국제학부("국제학부");


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