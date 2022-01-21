package com.wahyu.portofolio.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.ByteSource;
import com.wahyu.portofolio.model.BackendAuditrail;
import com.wahyu.portofolio.service.BackendAuditrailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class LoggableDispatcherServlet extends DispatcherServlet {

    @Autowired
    BackendAuditrailService backendAuditrailService;

    private String requestToken;

    @Override
    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!(request instanceof ContentCachingRequestWrapper)) {
            request = new ContentCachingRequestWrapper(request);
            setRequestToken(request.getHeader("authorization"));
        }
        if (!(response instanceof ContentCachingResponseWrapper)) {
            response = new ContentCachingResponseWrapper(response);
        }
        HandlerExecutionChain handler = getHandler(request);

        try {
            super.doDispatch(request, response);
        } finally {
            log(request, response, handler);
            updateResponse(response);
        }
    }

    private void log(HttpServletRequest requestToCache, HttpServletResponse responseToCache, HandlerExecutionChain handler) throws ParseException {
        BackendAuditrail backendAuditrail = new BackendAuditrail();
        /*
        get request parameter
         */
        final Map<String, String> pathVariables = (Map<String, String>) requestToCache.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
        Date date1 = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").parse(timeStamp);
        String reqBody = null;

        /*
        get request body
         */
        try{
            reqBody = ByteSource.wrap(((ContentCachingRequestWrapper) requestToCache).getContentAsByteArray())
                    .asCharSource(StandardCharsets.UTF_8).read();
        }catch (Exception e){
            e.getMessage();
        }

        String username = null;
        String token = null;

        try{
            token = getRequestToken().substring(7);
        }catch (Exception e){
            e.getMessage();
        }

        try {
            if (requestToCache.getRequestURI().equalsIgnoreCase("/user-management/signin")) {
                /* RESPONSE BODY TO MAP */
                ObjectMapper mapper = new ObjectMapper();
                String readResponse = ByteSource.wrap(((ContentCachingResponseWrapper) responseToCache).getContentAsByteArray())
                        .asCharSource(StandardCharsets.UTF_8).read();
                Map<String, String> responseToMap = mapper.readValue(readResponse, Map.class);
                reqBody = null;
                username = responseToMap.get("username");
                token = responseToMap.get("token");
            }else{
                username = SecurityContextHolder.getContext().getAuthentication().getName();
            }
        }catch (Exception e){
            e.getMessage();
        }

        backendAuditrail.setToken(token);
        backendAuditrail.setUsername(username);
        backendAuditrail.setRequestMethod(requestToCache.getMethod());
        backendAuditrail.setRequestApi(requestToCache.getRequestURL().toString());
        backendAuditrail.setResponseApi(String.valueOf(responseToCache.getStatus()));
        backendAuditrail.setRequestParameter(reqBody);
        backendAuditrail.setHitDate(date1);
        backendAuditrail.setIpAddress(getClientIP(requestToCache));
        backendAuditrailService.save(backendAuditrail);
    }

    private void updateResponse(HttpServletResponse response) throws IOException {
        ContentCachingResponseWrapper responseWrapper =
                WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        responseWrapper.copyBodyToResponse();
    }

    public String getRequestToken() {
        return requestToken;
    }

    public void setRequestToken(String requestToken) {
        this.requestToken = requestToken;
    }

    public String getClientIP(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null){
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0]; // voor als ie achter een proxy zit
    }
}
