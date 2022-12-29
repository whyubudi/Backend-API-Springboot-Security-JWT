package com.wahyu.portofolio.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import javax.persistence.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;

@Data
@Entity
@Table(name = "t_fraud_trans")
public class TransData {

    @Id
    @Column(name = "UTRNNO")
    private Long utrnno;
    private String hpan;
    @JsonFormat(pattern="dd.MM.yyyy HH:mm:ss")
    private Timestamp dateTime;
    @JsonSerialize(using = CustomAmountSerializer.class)
    private Long amount_1;
    @JsonFormat(pattern="dd.MM.yyyy HH:mm:ss")
    private Timestamp ttime;
    private String addressName;
    private String mcc;
    private String merchantId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UTRNNO", referencedColumnName = "ID", insertable = false)
    private IssuerAlerts issAlerts;
}

class CustomAmountSerializer extends JsonSerializer<Long> {

    @Override
    public void serialize(Long value, JsonGenerator jGen, SerializerProvider provider) throws IOException, JsonGenerationException {
        if (value == null) {
            jGen.writeNull();
        } else {
            String strValue = value.toString().substring(0,value.toString().length()-2);
            BigDecimal trueValue = new BigDecimal(strValue);
            final DecimalFormat formatter = new DecimalFormat("#,###,###,###.00");
            final String output = formatter.format(trueValue);
            jGen.writeString(output);
        }
    }
}
