package com.wahyu.portofolio.util;

public class InvestigationDecisionUtil {

    public static String ClassificationType  (int classificationType) {
        switch (classificationType) {
            case 0:
                return "Not Classification Type";
            case 1:
                return "Postponed";
            case 2:
                return "Negatif";
            case 3:
                return "Suspicious";
            case 4:
                return "Positive";
            default:
                return "";
        }
    }
}
