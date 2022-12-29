package com.wahyu.portofolio.util;

public class PhoneNumberUtil {

    private PhoneNumberUtil() {
    }

    public static String constructInternationalPhoneNumber(String phoneNumber) {
        String result = phoneNumber.replaceAll("\\D", "");
        if (result.startsWith("0")) {
            result = result.replaceFirst("0", "62");
        }
        return result;
    }
}
