package com.ll.JollyJourney.domain.auth.dto;

import com.ll.JollyJourney.global.enums.Gender;

import java.time.LocalDate;

public record ModifyInfoReq(
        String email,
        String newEmail,
        String newName,
        String newPhoneNumber,
        LocalDate newBirthDay,
        Gender newGender
){}
