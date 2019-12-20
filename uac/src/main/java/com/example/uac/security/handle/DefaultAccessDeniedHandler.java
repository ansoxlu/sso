package com.example.uac.security.handle;

import com.alibaba.fastjson.JSON;
import com.example.uac.security.model.ResultError;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  status: 403
 *  主要统一返回格式
 */
public class DefaultAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        ResultError resultError = new ResultError(HttpServletResponse.SC_FORBIDDEN, accessDeniedException.getMessage());
        response.getWriter().write(JSON.toJSONString(resultError));
    }
}
