package com.wahyu.portofolio.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Date;

@Data
@Entity
@Table(name = "t_fraud_trans")
public class TransData {

    @Id
    @Column(name = "UTRNNO")
    private Long utrnno;
    private String hpan;
    @JsonSerialize(as = Date.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp dateTime;
    @JsonSerialize(using = CustomAmountSerializer.class)
    private Long amount_1;
    @JsonSerialize(as = Date.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp ttime;
    private String addressName;
    private String mcc;
    private String merchantId;
    private String iss_inst;
    private String acqinstid;
    private String transType;
    private String terminalId;
    private String addressCity;
    private String countryCode;
    private String respCode;
    private String riskValue;
    private String rulesList;


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
