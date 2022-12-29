package com.wahyu.portofolio.constant;

import org.springframework.beans.factory.annotation.Value;

import java.nio.file.Path;

public class Constants {

    public static String PROJECT_URI = "http://localhost:8046/";

    public static final int MAX_REQUESTS_PER_SECOND = 10;
    /*
    ERROR CODE & MESSAGE
     */
    public static final String[] SUCCESS = {"00", "Success"};
    public static final String[] USERNAME_EXIST = {"83", "Username already exist!"};
    public static final String[] ROLE_NOT_AVAILABLE = {"84", "Role not available!"};
    public static final String[] METHOD_ERROR = {"02", "Method Error!"};
    public static final String[] INVALID_USERNAME_OR_PASSWORD = {"81", "Invalid Username or Password!"};
    public static final String[] INVALID_PASSWORD = {"82", "Invalid Password!"};
    public static final String[] PASSWORD_NOT_MATCH = {"83", "Password Not Match!"};
    public static final String[] EMAIL_EXIST = {"90", "Email already exist!"};
    public static final String[] INVALID_EMAIL = {"91", "Invalid Email!"};
    public static final String[] REQID_NOT_AVAILABLE = {"53", "Request ID not available!"};
    public static final String[] QUERY_UNDEFINE = {"54", "Query Undefine"};

    /* MASTER_STATUS */
    public static final int APPROVE = 54;
    public static final int WAITING_APPROVAL = 63;
    public static final int REJECT = 67;

    /* Multipart Configuration */
    public static Path UPLOAD_PATH;

    @Value("${file.upload-dir}")
    public static void setUploadPath(Path uploadPath) {
        Constants.UPLOAD_PATH = uploadPath;
    }
}
