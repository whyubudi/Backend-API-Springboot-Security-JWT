package com.wahyu.portofolio.dao;

import com.wahyu.portofolio.model.Constants;
import com.wahyu.portofolio.model.RequestForgotPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RequestForgotPasswordDao extends JpaRepository<RequestForgotPassword, String> {

    @Query("SELECT rfp "
            + "FROM RequestForgotPassword rfp WHERE rfp.reqId =:reqId and status="+ Constants.WAITING_APPROVAL)
    RequestForgotPassword reqIdIsWaitingApproval(@Param("reqId") String reqId);
}
