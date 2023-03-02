package com.wahyu.portofolio.controller;

import com.wahyu.portofolio.dao.DisableAlertRepo;
import com.wahyu.portofolio.dao.IssuerAlertsRepo;
import com.wahyu.portofolio.dao.RepoTransData;
import com.wahyu.portofolio.dto.alltransdata.GetTransDataDto;
import com.wahyu.portofolio.dto.alltransdata.GetTransDataRequest;
import com.wahyu.portofolio.dto.alltransdata.GetTransDataResponse;
import com.wahyu.portofolio.model.IssuerAlerts;
import com.wahyu.portofolio.specificator.SearchSpecificator;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/list")
@Slf4j
public class DataTableList {

    @Autowired
    RepoTransData repoTransData;

    @Autowired
    private IssuerAlertsRepo issuerAlertsRepo;

    @Autowired
    private DisableAlertRepo disableAlertRepo;

    @CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
    @PostMapping("/gettransdata")
    public GetTransDataResponse getTransData(@RequestBody GetTransDataRequest request) throws Exception {
        try {
            int actualPage = request.getPage() - 1;
            Pageable pageable = PageRequest.of(actualPage, request.getSize(), Sort.by("ttime").descending());

            Specification<IssuerAlerts> dataSearch = Specification.where(
                    request.getHpan() == null ? null : SearchSpecificator.findByHpan(request.getHpan())
            ).and(
                    request.getTransactionId() == null ? null : SearchSpecificator.findByTransactionId(request.getTransactionId())
            ).and(
                    request.getMcc() == null ? null : SearchSpecificator.findByMcc(request.getMcc())
            ).and(
                    request.getMerchantId() == null ? null : SearchSpecificator.findByMerchantId(request.getMerchantId())
            ).and(
                    request.getInvestigationDecision() == null ? null : SearchSpecificator.findByInvestigation(request.getInvestigationDecision())
            ).and(
                    request.getStartDate() == null && request.getEndDate() == null ? null : SearchSpecificator.findByDate(request.getStartDate(), request.getEndDate())
            ).and(
                    request.getIssuerInstitutionId() == null ? null : SearchSpecificator.findByIssuerInstitutionId(request.getIssuerInstitutionId())
            ).and(
                    request.getAcquirerInstitutionId() == null ? null : SearchSpecificator.findByAcquirerInstitutionId(request.getAcquirerInstitutionId())
            ).and(
                    request.getAmount_1() == null ? null : SearchSpecificator.findByAmount(request.getAmount_1())
            ).and(
                    request.getTransactionType() == null ? null : SearchSpecificator.findByTransactionType(request.getTransactionType())
            ).and(
                    request.getTerminalId() == null ? null : SearchSpecificator.findByTerminalId(request.getTerminalId())
            ).and(
                    request.getAddressName() == null ? null : SearchSpecificator.findByAddressName(request.getAddressName())
            ).and(
                    request.getAddressCity() == null ? null : SearchSpecificator.findByAddressCity(request.getAddressCity())
            ).and(
                    request.getCountryCode() == null ? null : SearchSpecificator.findByCountryCode(request.getCountryCode())
            ).and(
                    request.getRespCode() == null ? null : SearchSpecificator.findByRespCode(request.getRespCode())
            ).and(
                    request.getRiskValue() == null ? null : SearchSpecificator.findByRiskValue(request.getRiskValue())
            ).and(
                    request.getNip() == null ? null : SearchSpecificator.findByNip(request.getNip())
            ).and(
                    request.getRuleId() == null ? null : SearchSpecificator.findByRuleId(request.getRuleId())
            );


            log.info("response :{} ", issuerAlertsRepo.findAll(dataSearch, pageable).getContent());
            var result = issuerAlertsRepo.findAll(dataSearch, pageable);
            List<GetTransDataDto> transDto = new ArrayList<>();
            if (!result.isEmpty()) {
                for (IssuerAlerts object : result) {
                    var user = object.getUserStatusUpdate() == null ? "" : object.getUserStatusUpdate().getNip();
                    var disable = disableAlertRepo.findAllByHpan(request.getHpan());
                    Serializable disableUser;
                    if (disable.isEmpty()) {
                        disableUser = false;
                    } else {
                        disableUser = true;
                    }
                    transDto.add(GetTransDataDto.builder()
                            .utrnno(object.getTransData().getUtrnno())
                            .hpan(object.getTransData().getHpan())
                            .mcc(object.getTransData().getMcc())
                            .merchantId(object.getTransData().getMerchantId())
                            .iss_inst(object.getTransData().getIss_inst())
                            .acqinstid(object.getTransData().getAcqinstid())
                            .amount_1(object.getTransData().getAmount_1())
                            .transType(object.getTransData().getTransType())
                            .terminalId(object.getTransData().getTerminalId())
                            .addressName(object.getTransData().getAddressName())
                            .addressCity(object.getTransData().getAddressCity())
                            .countryCode(object.getTransData().getCountryCode())
                            .respCode(object.getTransData().getRespCode())
                            .riskValue(object.getTransData().getRiskValue())
                            .rulesList(object.getTransData().getRulesList())
                            .dateTime(object.getTransData().getDateTime())
                            .ttime(object.getTtime())
                            .typeAlert(object.getInvestigationDecision())
                            .user(user)
                            .disable(Boolean.valueOf(String.valueOf(disableUser)))
                            .build());
                }
            }

            assert result != null;
            return GetTransDataResponse.builder()
                    .getTransData(transDto)
                    .totalPages(result.getTotalPages())
                    .totalElements(result.getTotalElements())
                    .numberOfElements(result.getNumberOfElements())
                    .pageNumber(result.getNumber())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


//    @CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
//    @GetMapping("/alltransdata/{size}/{page}")
//    public AllTransDataResponse getAllTransData(@PathVariable int size, @PathVariable int page) throws Exception {
//        try {
//            int actualPage = page - 1;
//            Pageable transDataToShow = PageRequest.of(actualPage, size, Sort.by("ttime").descending());
//            Page<TransData> transData = repoTransData.getAllTransData(transDataToShow);
////            List<Memo> memo = transData.stream().map(data ->
////                    memoRepo.findByTransactionId(data.getUtrnno()).orElseThrow(() -> new ParameterNotFoundException("Data Not Found"))
////            ).collect(Collectors.toList());
//            List<Memo> memo = new ArrayList<>();
//            List<AllTransDataDto> listdto = new ArrayList<>();
//            Map<Long, Memo> transactionMemo = memo.stream().collect(Collectors.toMap(Memo::getTransactionId, Function.identity()));
//
//            listdto = transData.stream().map(trans -> {
//                AllTransDataDto allTransDataDto = new AllTransDataDto();
//                allTransDataDto.setUtrnno(trans.getUtrnno());
//                allTransDataDto.setHpan(trans.getHpan());
//                allTransDataDto.setDateTime(trans.getDateTime());
//                allTransDataDto.setAmount_1(trans.getAmount_1());
//                allTransDataDto.setTtime(trans.getTtime());
//                allTransDataDto.setMcc(trans.getMcc());
//                allTransDataDto.setMerchantId(trans.getMerchantId());
//                allTransDataDto.setTypeAlert(String.valueOf(trans.getIssAlerts().getInvestigationDecision()));
//
//                Memo memos = transactionMemo.get(trans.getUtrnno());
//
//                if (memos != null){
//                    DataMemoDto dataMemoDto = new DataMemoDto();
//                    dataMemoDto.setUser(memos.getNip());
//                    allTransDataDto.setMemoDto(dataMemoDto);
//                }
//
//                return allTransDataDto;
//            }).collect(Collectors.toList());
//
////            for (TransData trans : bytrans) {
////                log.info("Looping");
////                listdto.add(AllTransDataDto.builder()
////                        .utrnno(trans.getUtrnno())
////                        .hpan(trans.getHpan())
////                        .dateTime(trans.getDateTime())
////                        .amount_1(trans.getAmount_1())
////                        .ttime(trans.getTtime())
////                        .merchantId(trans.getMerchantId())
////                        .mcc(trans.getMcc())
////                        .user(String.valueOf(hasil))
////                        .typeAlert(String.valueOf(trans.getIssAlerts().getInvestigationDecision()))
////                        .build());
//
//            return AllTransDataResponse.builder()
//                    .allTransDataDtoList(listdto)
//                    .totalPages(transData.getTotalPages())
//                    .totalElements(transData.getTotalElements())
//                    .numberOfElements(transData.getNumberOfElements())
//                    .pageNumber(transData.getNumber())
//                    .build();
//        }catch (Exception e) {
//            e.printStackTrace();
//            throw e;
//        }
//    }

}
