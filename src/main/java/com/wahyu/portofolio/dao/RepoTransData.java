package com.wahyu.portofolio.dao;

import com.wahyu.portofolio.model.TransData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RepoTransData extends JpaRepository<TransData, Integer>, JpaSpecificationExecutor<TransData> {


    @Query(value = "SELECT a FROM TransData a INNER JOIN IssuerAlerts b ON a.utrnno = b.id " +
            "LEFT JOIN Memo c ON b.id = c.transactionId")
    Page<TransData> getAllTransData(String utrnno, String hpan, String datetime, String amount_1,
                                    String ttime, String mcc, String merchantid, String typealert,
                                    String user, String iss_inst, String acqinstid, String transtype,
                                    String terminalid, String addressname, String addresscity,
                                    String countrycode, String respcode, String riskvalue, String ruleslist,
                                    Specification<TransData> dataSearch, Pageable pageable);


    @Query(value = "SELECT a.utrnno,a.hpan,a.mcc,a.merchantId, a.iss_inst, a.acqinstid, a.amount_1, " +
            "a.transType, a.terminalId, a.addressName, a.addressCity, a.countryCode, a.respCode, " +
            "a.riskValue, a.rulesList, a.dateTime, a.ttime, b.investigationDecision, c.nip " +
            "FROM TransData a INNER JOIN IssuerAlerts b ON a.utrnno = b.id " +
            "LEFT JOIN Memo c ON b.id = c.transactionId")
    Page<Object[]> fetchAlertTransData(Pageable pageable);

    @Query(value = "SELECT DISTINCT b.hpan FROM IssuerAlerts a INNER JOIN TransData b ON a.id = b.utrnno ")
    Page<TransData> fetchDataByHpan(Pageable pageable);

    @Query(value = "SELECT b FROM IssuerAlerts a INNER JOIN TransData b ON a.id = b.utrnno " +
            "WHERE b.hpan LIKE:cardnum AND (b.dateTime BETWEEN TO_DATE(:datetime, 'YYYY-MM-DD') " +
            "AND TO_DATE(:datetime, 'YYYY-MM-DD') + 23/24) ORDER BY b.dateTime DESC")
    List<TransData> fetchAlertDetailTrans(@Param("cardnum")String hpan,
                                          @Param("datetime")String datetime);


    @Query(value = "SELECT a FROM TransData a LEFT JOIN IssuerAlerts b ON a.utrnno = b.id ")
    Page<TransData> findAllByData(Pageable pageable);

    @Query(value = "SELECT a FROM TransData a LEFT JOIN IssuerAlerts b ON a.utrnno = b.id " +
            "WHERE a.hpan LIKE:hpan  ORDER BY a.dateTime DESC")
    Page<TransData> findAllByHpan(@Param("hpan") String hpan,
                                  Pageable pageable);

    @Query(value = "SELECT a FROM TransData a LEFT JOIN IssuerAlerts b ON a.utrnno = b.id " +
            "WHERE a.mcc LIKE:mcc  ORDER BY a.dateTime DESC")
    Page<TransData> findAllByMcc(@Param("mcc") String mcc,
                                  Pageable pageable);
    @Query(value = "SELECT a FROM TransData a LEFT JOIN IssuerAlerts b ON a.utrnno = b.id " +
            "WHERE a.merchantId LIKE:merchantId  ORDER BY a.dateTime DESC")
    Page<TransData> findAllByMerchantId(@Param("merchantId") String merchantId,
                                        Pageable pageable);

//    @Query(value = "SELECT a FROM TransData a LEFT JOIN IssuerAlerts b ON a.utrnno = b.id " +
//            "WHERE b.investigationDecision LIKE:investigationDecision  ORDER BY a.dateTime DESC")
//    Page<TransData> findAllByInvestigation(@Param("investigationDecision") String investigationDecision,
//                                            Pageable pageable);

    @Query(value = "SELECT a FROM TransData a LEFT JOIN IssuerAlerts b ON a.utrnno = b.id " +
            "WHERE a.hpan LIKE:hpan " +
            "AND (a.dateTime BETWEEN TO_DATE(:dateTimeStart, 'YYYY-MM-DD') " +
            "AND TO_DATE(:dateTimeEnd, 'YYYY-MM-DD') +23/24) ORDER BY a.dateTime DESC")
    Page<TransData> findAllByHpanAndDateTimeOrderByDateTimeDesc(@Param("hpan") String hpan,
                                                                  @Param("dateTimeStart") String dateTimeStart,
                                                                  @Param("dateTimeEnd") String dateTimeEnd,
                                                                  Pageable pageable);
    @Query(value = "SELECT a FROM TransData a LEFT JOIN IssuerAlerts b ON a.utrnno = b.id " +
            "WHERE (a.dateTime BETWEEN TO_DATE(:dateTimeStart, 'YYYY-MM-DD') " +
            "AND TO_DATE(:dateTimeEnd, 'YYYY-MM-DD') +23/24) ORDER BY a.dateTime DESC")
    Page<TransData> findAllByHpanAndMccAndMerchantIdAndDateTimeOrderByDateTimeDesc(@Param("dateTimeStart") String dateTimeStart,
                                                                                   @Param("dateTimeEnd") String dateTimeEnd,
                                                                                   Pageable pageable);
    @Query(value = "SELECT a FROM TransData a LEFT JOIN IssuerAlerts b ON a.utrnno = b.id " +
            "WHERE a.mcc LIKE:mcc " +
            "AND (a.dateTime BETWEEN TO_DATE(:dateTimeStart, 'YYYY-MM-DD') " +
            "AND TO_DATE(:dateTimeEnd, 'YYYY-MM-DD') +23/24) ORDER BY a.dateTime DESC")
    Page<TransData> findAllByMccAndDateTimeOrderByDateTimeDesc(@Param("mcc") String mcc,
                                                                @Param("dateTimeStart") String dateTimeStart,
                                                                @Param("dateTimeEnd") String dateTimeEnd,
                                                                Pageable pageable);
    @Query(value = "SELECT a FROM TransData a LEFT JOIN IssuerAlerts b ON a.utrnno = b.id " +
            "WHERE a.merchantId LIKE:merchantId " +
            "AND (a.dateTime BETWEEN TO_DATE(:dateTimeStart, 'YYYY-MM-DD') " +
            "AND TO_DATE(:dateTimeEnd, 'YYYY-MM-DD') +23/24) ORDER BY a.dateTime DESC")
    Page<TransData> findAllByMerchantIdAndDateTimeOrderByDateTimeDesc(@Param("merchantId") String merchantId,
                                                                        @Param("dateTimeStart") String dateTimeStart,
                                                                        @Param("dateTimeEnd") String dateTimeEnd,
                                                                        Pageable pageable);
//    @Query(value = "SELECT a FROM TransData a LEFT JOIN IssuerAlerts b ON a.utrnno = b.id " +
//            "WHERE b.investigationDecision LIKE:investigationDecision " +
//            "AND (a.dateTime BETWEEN TO_DATE(:dateTimeStart, 'YYYY-MM-DD') " +
//            "AND TO_DATE(:dateTimeEnd, 'YYYY-MM-DD') +23/24) ORDER BY a.dateTime DESC")
//    Page<TransData> findAllByInvestigationAndDateTimeOrderByDateTimeDesc(@Param("investigationDecision") String investigationDecision,
//                                                                         @Param("dateTimeStart") String dateTimeStart,
//                                                                         @Param("dateTimeEnd") String dateTimeEnd,
//                                                                         Pageable pageable);

}
