package com.wahyu.portofolio.dto.email;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailRequest implements Serializable {
    private static final long serialVersionUID = 1986375694270761859L;

    private String recipient;
    private String msgText;
    private String subject;
}
