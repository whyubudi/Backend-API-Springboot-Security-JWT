package com.wahyu.portofolio.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.text.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@Service
public class HelperService {

    public String encryptPassword(String password){
        int strength = 31;
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(strength, new SecureRandom());
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        return encodedPassword;
    }

    public Date getDateNow() throws ParseException {
        String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
        Date dateNow = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").parse(timeStamp);
        return dateNow;
    }

    public String setThousandSeparator(BigDecimal value, char separator) {
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();

        symbols.setGroupingSeparator(separator);
        formatter.setDecimalFormatSymbols(symbols);

        return formatter.format(value.longValue());
    }
}
