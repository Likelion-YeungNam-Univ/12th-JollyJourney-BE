package com.ll.JollyJourney.domain.member.member.entity;

import lombok.Getter;

@Getter
public enum MemberRole {
    MEMBER("MEMBER"),
    ADMIN("ADMIN"),
    SUPER_VISOR("SUPER_VISOR");

    private final String value;

    MemberRole(String value) {
        this.value = value;
    }
}
