package com.wahyu.portofolio.specificator;

import com.wahyu.portofolio.model.IssuerAlerts;
import com.wahyu.portofolio.model.TransData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
public class DataSearchSpecificator {

        public static Specification<TransData> findByHpan(String hpan){
            return ((root, query, builder) -> builder.like(root.get("hpan"),hpan));
        }

        public static Specification<TransData> findByMcc(String... mcc){
            return (root, query, builder) -> builder.or(Arrays.stream(mcc)
                    .map(mc -> builder.equal(root.get("mcc"), mc)).toArray(Predicate[]::new));
        }

        public static Specification<TransData> findByMerchantId(String merchantId){
            return ((root, query, builder) -> builder.like(root.get("merchantId"),contains(merchantId)));
        }

    public static Specification<TransData> findByInvestigationDecision(String investigationDecision) {
        return (root, query, criteriaBuilder) -> {
            Join<TransData, IssuerAlerts> investJoin = root.join("issAlerts");
            return criteriaBuilder.like(investJoin.get("investigationDecision"), investigationDecision);
        };
    }

    private static Timestamp localToTimeStamp(LocalDate date) {
        return Timestamp.from(date.atStartOfDay().toInstant(ZoneOffset.UTC));
    }

    public static Specification<TransData> findByDate(String startDate, String endDate) {
        log.info("startdate : {} ", startDate);
        log.info("endDate : {} ", endDate);
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (startDate != null && endDate != null) {
                Date fromDate = localToTimeStamp(LocalDate.parse(startDate));
                Date toDate = localToTimeStamp(LocalDate.parse(endDate));
                log.info("fromDate : {} ", fromDate);
                log.info("toDate : {} ", toDate);
                predicates.add(criteriaBuilder.between(root.get("dateTime"), fromDate, toDate));
            }
            log.info("predicate : {} ", predicates.size());
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static String contains(String expression) {
        return MessageFormat.format("%{0}%",expression);
    }

}
