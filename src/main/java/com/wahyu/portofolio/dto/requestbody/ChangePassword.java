package com.wahyu.portofolio.dto.requestbody;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePassword {

    private String oldPassword;

    private String newPassword;

    private String repeatNewPassword;
}
