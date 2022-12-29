package com.wahyu.portofolio.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
@ConditionalOnProperty("scheduler.enable")
public class GenerateXML {

    @Value("${cc.datasource.driver-class-name}")
    private String driverSqlSrv;
    @Value("${cc.datasource.url}")
    private String urlDataCredit;
    @Value("${cc.datasource.username}")
    private String userDataCredit;
    @Value("${cc.datasource.password}")
    private String passDataCredit;

    @Value("${dc.datasource.driver-class-name}")
    private String driverOracle;
    @Value("${dc.datasource.url}")
    private String urlDataDebit;
    @Value("${dc.datasource.username}")
    private String userDataDebit;
    @Value("${dc.datasource.password}")
    private String passDataDebit;

    private Logger logger = LoggerFactory.getLogger(getClass());

    protected Connection getConnectionDWH(){
        Connection conn = null;
        try {
            Class.forName(driverSqlSrv);
            conn = DriverManager.getConnection(urlDataCredit,userDataCredit,passDataCredit);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            logger.error("Error: {} " , e.getMessage());
        }
        return conn;
    }

    protected Connection getConnectionDCMS(){
        Connection conn = null;
        try {
            Class.forName(driverOracle);
            conn = DriverManager.getConnection(urlDataDebit,userDataDebit,passDataDebit);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            logger.error("Error: {} " , e.getMessage());
        }
        return conn;
    }

//    @Scheduled(cron = "${scheduler.generate.xmlfile}")
    public void dataDemografiCredit(){

        Connection dwhConn = getConnectionDWH();
        PreparedStatement ps;
        ResultSet rs;

        try {
            logger.info("READ DEMOGRAFI DATA FROM DB DWH");
            String sql = "SELECT a.CARD_NBR, a.CARD_ACCT_NBR, a.CARD_EMB_NAME1, CAST(a.CARD_DTE_OPEN AS VARCHAR(20)) AS 'CARD_DTE_OPEN',\n" +
                    "a.CARD_STATUS, CAST(a.CARD_EXP_DATE AS VARCHAR(20)) AS 'CARD_EXP_DATE',\n" +
                    "c.CUST_NBR, c.CUST_TYPE, c.CUST_ID_NBR, CAST(c.CUST_DTE_BIRTH AS VARCHAR(20)) AS 'CUST_DTE_BIRTH', \n" +
                    "c.CUST_SEX, c.CUST_EMAIL_ADDR, c.CUST_MOBILE_PHONE,\n" +
                    "c.CUST_ADDR1, c.CUST_ADDR2, c.CUST_ADDR3, c.CUST_ADDR4, c.CUST_ADD_CITY, c.CUST_ADD_ZIPCODE \n" +
                    "FROM CURRENT_CC_SCMCARDP a \n" +
                    "LEFT JOIN CURRENT_CC_SCMCACCP b ON b.CRDACCT_NBR = a.CARD_ACCT_NBR \n" +
                    "LEFT JOIN CURRENT_CC_SCMCUSTP c ON c.CUST_NBR = b.CRDACCT_CUST_NBR\n" +
                    "WHERE a.CARD_NBR IN ('4201920054522006')";
            logger.info("SQL QUERY: {}",sql);
            ps = dwhConn.prepareStatement(sql);
            rs = ps.executeQuery();
        } catch (SQLException e) {
            logger.error("Error: {} " , e.getMessage());
            e.printStackTrace();
        }
    }

//    @Scheduled(cron = "0 0 8 * * ?")
//    public void dataDemografiCreditDaily(){
//
//        Connection dwhConn = getConnectionDWH();
//        PreparedStatement ps;
//        ResultSet rs;
//
//        try {
//            logger.info("READ DEMOGRAFI DATA FROM DB DWH");
//            String sql = "SELECT RTRIM(a.CARD_NBR) AS 'CARD_NBR', a.CARD_ACCT_NBR, CAST(a.CARD_DTE_OPEN AS VARCHAR(20)) AS 'CARD_DTE_OPEN', CAST(a.CARD_EXP_DATE AS VARCHAR(20)) AS 'CARD_EXP_DATE', \n" +
//                    "a.CARD_STATUS, b.CRDACCT_CRLIMIT, c.CUST_SEX, c.CUST_ANN_SALARY, c.CUST_CURR_CODE, a.CARD_PDT_NBR, b.CRDACCT_BRANCH_NBR, c.CUST_NBR, \n" +
//                    "c.CUST_TYPE, RTRIM(c.CUST_ID_NBR) AS 'CUST_ID_NBR', RTRIM(a.CARD_SUP_REL) AS 'CARD_SUP_REL', RTRIM(a.CARD_BLK_CODE) AS 'CARD_BLK_CODE', \n" +
//                    "RTRIM(b.CRDACCT_BLK_CODE) AS 'CRDACCT_BLK_CODE', \n" +
//                    "CASE \n" +
//                    "WHEN a.CARD_SUP_REL LIKE 'S%' THEN (SELECT TOP 1 REPLACE(CAST(CONCAT(RTRIM(ccs.CARD_EMB_NAME1),'|',CAST(ccs3.CUST_DTE_BIRTH AS VARCHAR(20)),'|',\n" +
//                    "RTRIM(ccs3.CUST_ID_NBR),'|',RTRIM(ccs3.CUST_EMAIL_ADDR),'|',RTRIM(ccs3.CUST_MOBILE_PHONE),'|',\n" +
//                    "RTRIM(ccs3.CUST_ADD_CITY),'|',RTRIM(ccs3.CUST_ADD_ZIPCODE),'|',RTRIM(ccs3.CUST_ADDR1),', ',\n" +
//                    "RTRIM(ccs3.CUST_ADDR2),', ',RTRIM(ccs3.CUST_ADDR3),', ',RTRIM(ccs3.CUST_ADDR4),'|',\n" +
//                    "RTRIM(ccs3.CUST_EMP_NAME),', ',RTRIM(ccs3.CUST_EMP_ADDR1),', ',RTRIM(ccs3.CUST_EMP_ADDR2),', ',\n" +
//                    "RTRIM(ccs3.CUST_EMP_ADDR3),', ',RTRIM(ccs3.CUST_EMP_ADDR4),', ',RTRIM(ccs3.CUST_EMP_CITY),' ',\n" +
//                    "RTRIM(ccs3.CUST_EMP_ZIP)) AS VARCHAR(MAX)) COLLATE SQL_Latin1_General_CP1_CI_AS,CHAR(160),'') \n" +
//                    "FROM CURRENT_CC_SCMCARDP ccs LEFT JOIN CURRENT_CC_SCMCACCP ccs2 ON ccs2.CRDACCT_NBR = ccs.CARD_ACCT_NBR \n" +
//                    "LEFT JOIN CURRENT_CC_SCMCUSTP ccs3 ON ccs3.CUST_NBR = ccs2.CRDACCT_CUST_NBR \n" +
//                    "WHERE ccs.CARD_ACCT_NBR IN (a.CARD_ACCT_NBR) AND ccs.CARD_SUP_REL LIKE 'P%') \n" +
//                    "ELSE REPLACE(CAST(CONCAT(RTRIM(a.CARD_EMB_NAME1),'|',CAST(c.CUST_DTE_BIRTH AS VARCHAR(20)),'|',RTRIM(c.CUST_ID_NBR),'|',RTRIM(c.CUST_EMAIL_ADDR),'|',\n" +
//                    "RTRIM(c.CUST_MOBILE_PHONE),'|',RTRIM(c.CUST_ADD_CITY),'|',RTRIM(c.CUST_ADD_ZIPCODE),'|',RTRIM(c.CUST_ADDR1),', ',RTRIM(c.CUST_ADDR2),', ',\n" +
//                    "RTRIM(c.CUST_ADDR3),', ',RTRIM(c.CUST_ADDR4),'|',RTRIM(c.CUST_EMP_NAME),', ',RTRIM(c.CUST_EMP_ADDR1),', ',RTRIM(c.CUST_EMP_ADDR2),', ',\n" +
//                    "RTRIM(c.CUST_EMP_ADDR3),', ',RTRIM(c.CUST_EMP_ADDR4),', ',RTRIM(c.CUST_EMP_CITY),' ',RTRIM(c.CUST_EMP_ZIP)) \n" +
//                    "AS VARCHAR(MAX)) COLLATE SQL_Latin1_General_CP1_CI_AS,CHAR(160),'') \n" +
//                    "END AS 'CUST_DATA' \n" +
//                    "FROM CURRENT_CC_SCMCARDP a \n" +
//                    "LEFT JOIN CURRENT_CC_SCMCACCP b ON b.CRDACCT_NBR = a.CARD_ACCT_NBR \n" +
//                    "LEFT JOIN CURRENT_CC_SCMCUSTP c ON c.CUST_NBR = b.CRDACCT_CUST_NBR \n" +
//                    "WHERE a.CARD_DATE_MAINT = {DATE} AND b.CRDACCT_DATE_MAINT = {DATE}\n" +
//                    "ORDER BY a.CARD_NBR OFFSET {ROWSTART} ROWS FETCH NEXT {PAGE} ROWS ONLY";
//            logger.info("SQL QUERY: {}",sql);
//            ps = dwhConn.prepareStatement(sql);
//            rs = ps.executeQuery();
//        } catch (SQLException e) {
//            logger.error("Error: {} " , e.getMessage());
//            e.printStackTrace();
//        }
//    }
}
