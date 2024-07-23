package com.ll.JollyJourney.domain.member.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginFormDto {

    private String email;
    private String password;

}
