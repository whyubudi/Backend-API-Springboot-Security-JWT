package com.wahyu.portofolio.dto.responsebody;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInResponse {

    private String username;

    private String token;
}
