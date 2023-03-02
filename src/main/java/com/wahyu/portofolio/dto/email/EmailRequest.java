package com.wahyu.portofolio.dto.email;

import com.sun.istack.NotNull;
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

    @NotNull
    private String recipient;
    private String msgText;
    private String subject;
    private String user;
}
