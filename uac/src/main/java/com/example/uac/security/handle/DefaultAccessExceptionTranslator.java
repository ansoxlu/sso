package com.example.uac.security.handle;

import com.example.uac.security.model.ResultError;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;

/**
 *  status: 400
 *  认证失败后结果处理， 主要统一返回格式
 */
public class DefaultAccessExceptionTranslator extends DefaultWebResponseExceptionTranslator {
    @Override
    public ResponseEntity translate(Exception e) throws Exception {
        ResponseEntity<OAuth2Exception> responseEntity = super.translate(e);
        OAuth2Exception entityBody = responseEntity.getBody();
        ResultError error = new ResultError(entityBody.getHttpErrorCode(), entityBody.getMessage());
        return new ResponseEntity<>(error, responseEntity.getHeaders(), responseEntity.getStatusCode());
    }


}
