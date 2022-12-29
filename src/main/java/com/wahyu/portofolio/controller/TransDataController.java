package com.wahyu.portofolio.controller;

import com.wahyu.portofolio.dao.AttributeDao;
import com.wahyu.portofolio.dao.IssuerAlertsDao;
import com.wahyu.portofolio.dao.TransDataDao;
import com.wahyu.portofolio.dto.investigation.InvestigationRequest;
import com.wahyu.portofolio.dto.investigation.InvestigationResponse;
import com.wahyu.portofolio.dto.search.SearchRequest;
import com.wahyu.portofolio.exeption.ParameterNotFoundException;
import com.wahyu.portofolio.model.TransData;
import com.wahyu.portofolio.specificator.DataSearchSpecificator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/list")
@Slf4j
public class TransDataController {

    @Autowired
    private TransDataDao transDataDao;

    @Autowired
    private AttributeDao attributeDao;

    @Autowired
    private IssuerAlertsDao issuerAlertsDao;

    @CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
    @GetMapping("/alltransdata/{size}/{page}")
    public Page<TransData> getAllTransData(@PathVariable int size, @PathVariable int page) {

        int actualPage = page - 1;
        Pageable transDataToShow = PageRequest.of(actualPage,size, Sort.by("ttime").descending());
        return transDataDao.fetchAlertTransData(transDataToShow);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
    @GetMapping("/distinctcardnum/{size}/{page}")
    public Page<TransData> getDistinctCardNum(@PathVariable int size, @PathVariable int page) {

        int actualPage = page - 1;
        Pageable transDataToShow = PageRequest.of(actualPage,size);
        return transDataDao.fetchDataByHpan(transDataToShow);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
    @PostMapping("/detailtrans")
    public List<TransData> detailTrans(@RequestBody Map<String, Object> req){
        try {
            log.info("request cardnum & datetime : {} ", req);
            List<TransData> transData = transDataDao.fetchAlertDetailTrans(req.get("cardnum").toString() , req.get("datetime").toString());
            transData.forEach((td) -> td.setMcc(td.getMcc() + " (" + attributeDao.findAllByListValue(td.getMcc()).getDescription() + ")"));
            return transData;
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
    @PostMapping("/search")
    public Page<TransData> search (@RequestBody SearchRequest request) throws Exception {
        try {
            log.info("request hpan, mcc, merchantid, investigationdecision, datetime : {} ", request);
            int actualPage = request.getPage() - 1;
            Pageable pageable = PageRequest.of(actualPage, request.getSize(),Sort.by("ttime").descending());
            Specification<TransData> dataSearch = Specification.where(
                    request.getHpan() == null ? null : DataSearchSpecificator.findByHpan(request.getHpan())
            ).and(
                    request.getMcc() == null ? null : DataSearchSpecificator.findByMcc(request.getMcc().split(","))
            ).and(
                    request.getMerchantId() == null ? null : DataSearchSpecificator.findByMerchantId(request.getMerchantId())
            ).and(
                    request.getInvestigationDecision() == null ? null : DataSearchSpecificator.findByInvestigationDecision(request.getInvestigationDecision())
            ).and(
                    request.getStartDate() == null && request.getEndDate() == null ? null : DataSearchSpecificator.findByDate(request.getStartDate(),request.getEndDate())
            );

            return transDataDao.findAll(dataSearch,pageable);
        } catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
    @PostMapping("/updateInvestigationDecision")
    public InvestigationResponse updateInvestigationDecision(@RequestBody InvestigationRequest request) throws Exception {
        try {
            log.info("Request Id : {} ", request);
            var stat = issuerAlertsDao.findById(request.getId()).orElseThrow(() -> new ParameterNotFoundException("Id Not Found"));
            stat.setInvestigationDecision(request.getInvestigationDecision());
            var updateInvestigation = issuerAlertsDao.save(stat);

            log.info("Response : {} ", updateInvestigation);
            return InvestigationResponse.builder().issuerAlerts(updateInvestigation).build();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }
}
