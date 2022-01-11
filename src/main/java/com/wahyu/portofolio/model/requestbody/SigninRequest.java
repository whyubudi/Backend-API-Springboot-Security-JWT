package com.wahyu.portofolio.model.requestbody;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SigninRequest {

    private String username;

    private String password;
}
