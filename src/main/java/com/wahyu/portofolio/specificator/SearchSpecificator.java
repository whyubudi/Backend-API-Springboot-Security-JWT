package com.wahyu.portofolio.specificator;

import com.wahyu.portofolio.model.IssuerAlerts;
import com.wahyu.portofolio.model.TransData;
import com.wahyu.portofolio.model.UserStatusUpdate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
public class SearchSpecificator {

//    public static Specification<IssuerAlerts> findByTransactionId(String transaction){
//        return ((root, query, builder) -> builder.like(root.get("id"),contains(transaction)));
//    }

    public static Specification<IssuerAlerts> findByTransactionId(String transaction) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(transaction)
                        ? builder.conjunction()
                        : builder.like(builder.function("TO_CHAR", String.class, root.get("id")), contains(String.valueOf(transaction)));
    }

//    public static Specification<IssuerAlerts> findByInvestigation(Integer investigation){
//        return ((root, query, builder) -> builder.like(root.get("investigationDecision"),contains(String.valueOf(investigation))));
//    }

    public static Specification<IssuerAlerts> findByInvestigation(Integer investigation) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(investigation)
                        ? builder.conjunction()
                        : builder.like(builder.function("TO_CHAR", String.class, root.get("investigationDecision")), contains(String.valueOf(investigation)));
    }

    public static Specification<IssuerAlerts> findByHpan(String hpan){
        return ((root, query, builder) -> {
            List<Predicate> list = new ArrayList<Predicate>();
            Join<IssuerAlerts, TransData> card = root.join("transData", JoinType.INNER);

            list.add(builder.like(card.get("hpan"), contains(hpan)));
            Predicate[] p = new Predicate[list.size()];
            return builder.and(list.toArray(p));
        });
    }


    public static Specification<IssuerAlerts> findByAmount(String amount){
        return ((root, query, builder) -> {
            List<Predicate> list = new ArrayList<Predicate>();
            Join<IssuerAlerts, TransData> amon = root.join("transData", JoinType.INNER);

            list.add(builder.like(amon.get("amount_1"), contains(amount)));
            Predicate[] p = new Predicate[list.size()];
            return builder.and(list.toArray(p));
        });
    }

    public static Specification<IssuerAlerts> findByAddressName(String addressname){
        return ((root, query, builder) -> {
            List<Predicate> list = new ArrayList<Predicate>();
            Join<IssuerAlerts, TransData> addrsname = root.join("transData", JoinType.INNER);

            list.add(builder.like(addrsname.get("addressName"), contains(addressname)));
            Predicate[] p = new Predicate[list.size()];
            return builder.and(list.toArray(p));
        });
    }

    public static Specification<IssuerAlerts> findByMcc(String mcc){
        return ((root, query, builder) -> {
            List<Predicate> list = new ArrayList<Predicate>();
            Join<IssuerAlerts, TransData> mc = root.join("transData", JoinType.INNER);

            list.add(builder.like(mc.get("mcc"), contains(mcc)));
            Predicate[] p = new Predicate[list.size()];
            return builder.and(list.toArray(p));
        });
    }

    public static Specification<IssuerAlerts> findByMerchantId(String merchantid){
        return ((root, query, builder) -> {
            List<Predicate> list = new ArrayList<Predicate>();
            Join<IssuerAlerts, TransData> merchant = root.join("transData", JoinType.INNER);

            list.add(builder.like(merchant.get("merchantId"), contains(merchantid)));
            Predicate[] p = new Predicate[list.size()];
            return builder.and(list.toArray(p));
        });
    }

    public static Specification<IssuerAlerts> findByIssuerInstitutionId(String iss){
        return ((root, query, builder) -> {
            List<Predicate> list = new ArrayList<Predicate>();
            Join<IssuerAlerts, TransData> instituion = root.join("transData", JoinType.INNER);

            list.add(builder.like(instituion.get("iss_inst"), contains(iss)));
            Predicate[] p = new Predicate[list.size()];
            return builder.and(list.toArray(p));
        });
    }

    public static Specification<IssuerAlerts> findByAcquirerInstitutionId(String acq){
        return ((root, query, builder) -> {
            List<Predicate> list = new ArrayList<Predicate>();
            Join<IssuerAlerts, TransData> acquirer = root.join("transData", JoinType.INNER);

            list.add(builder.like(acquirer.get("acqinstid"), contains(acq)));
            Predicate[] p = new Predicate[list.size()];
            return builder.and(list.toArray(p));
        });
    }

    public static Specification<IssuerAlerts> findByTransactionType(String transactionType){
        return ((root, query, builder) -> {
            List<Predicate> list = new ArrayList<Predicate>();
            Join<IssuerAlerts, TransData> type = root.join("transData", JoinType.INNER);

            list.add(builder.like(type.get("transType"), contains(transactionType)));
            Predicate[] p = new Predicate[list.size()];
            return builder.and(list.toArray(p));
        });
    }

    public static Specification<IssuerAlerts> findByTerminalId(String terminalId){
        return ((root, query, builder) -> {
            List<Predicate> list = new ArrayList<Predicate>();
            Join<IssuerAlerts, TransData> terminal = root.join("transData", JoinType.INNER);

            list.add(builder.like(terminal.get("terminalId"), contains(terminalId)));
            Predicate[] p = new Predicate[list.size()];
            return builder.and(list.toArray(p));
        });
    }

    public static Specification<IssuerAlerts> findByAddressCity(String addressCity){
        return ((root, query, builder) -> {
            List<Predicate> list = new ArrayList<Predicate>();
            Join<IssuerAlerts, TransData> city = root.join("transData", JoinType.INNER);

            list.add(builder.like(city.get("addressCity"), contains(addressCity)));
            Predicate[] p = new Predicate[list.size()];
            return builder.and(list.toArray(p));
        });
    }

    public static Specification<IssuerAlerts> findByCountryCode(String countryCode){
        return ((root, query, builder) -> {
            List<Predicate> list = new ArrayList<Predicate>();
            Join<IssuerAlerts, TransData> country = root.join("transData", JoinType.INNER);

            list.add(builder.like(country.get("countryCode"), contains(countryCode)));
            Predicate[] p = new Predicate[list.size()];
            return builder.and(list.toArray(p));
        });
    }

    public static Specification<IssuerAlerts> findByRespCode(String respCode){
        return ((root, query, builder) -> {
            List<Predicate> list = new ArrayList<Predicate>();
            Join<IssuerAlerts, TransData> respcode = root.join("transData", JoinType.INNER);

            list.add(builder.like(respcode.get("respCode"), contains(respCode)));
            Predicate[] p = new Predicate[list.size()];
            return builder.and(list.toArray(p));
        });
    }

    public static Specification<IssuerAlerts> findByRiskValue(String riskValue){
        return ((root, query, builder) -> {
            List<Predicate> list = new ArrayList<Predicate>();
            Join<IssuerAlerts, TransData> risk = root.join("transData", JoinType.INNER);

            list.add(builder.like(risk.get("riskValue"), contains(riskValue)));
            Predicate[] p = new Predicate[list.size()];
            return builder.and(list.toArray(p));
        });
    }


    public static Specification<IssuerAlerts> findByNip(String nip){
        return ((root, query, builder) -> {
            List<Predicate> list = new ArrayList<>();
            Join<IssuerAlerts, UserStatusUpdate> user = root.join("userStatusUpdate", JoinType.LEFT);

            list.add(builder.like(user.get("nip"), contains(nip)));
            Predicate[] p = new Predicate[list.size()];
            log.info("predicate : {} ", p.length);
            return builder.and(list.toArray(p));
        });
    }

    public static Specification<IssuerAlerts> findByRuleId(String ruleId){
        return ((root, query, builder) -> {
            List<Predicate> list = new ArrayList<Predicate>();
            Join<IssuerAlerts, TransData> rule = root.join("transData", JoinType.INNER);

            list.add(builder.like(rule.get("rulesList"), contains(ruleId)));
            Predicate[] p = new Predicate[list.size()];
            return builder.and(list.toArray(p));
        });
    }

    private static Timestamp localToTimeStamp(LocalDate date) {
        return Timestamp.from(date.atStartOfDay().toInstant(ZoneOffset.UTC));
    }

    public static Specification<IssuerAlerts> findByDate(String startDate, String endDate) {
        log.info("startdate : {} ", startDate);
        log.info("endDate : {} ", endDate);
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Join<IssuerAlerts, TransData> date = root.join("transData", JoinType.INNER);

            if (startDate != null && endDate != null) {
                Date fromDate = localToTimeStamp(LocalDate.parse(startDate));
                Date toDate = localToTimeStamp(LocalDate.parse(endDate).plusDays(1));
                log.info("fromDate : {} ", fromDate);
                log.info("toDate : {} ", toDate);
                predicates.add(criteriaBuilder.between(date.get("dateTime"), fromDate, toDate));
            }
            log.info("predicate : {} ", predicates.size());
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static String contains(String expression) {
        return MessageFormat.format("%{0}%",expression);
    }


}
