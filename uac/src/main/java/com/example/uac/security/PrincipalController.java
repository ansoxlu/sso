package com.example.uac.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Enumeration;

@RestController
@Slf4j
public class PrincipalController {
    @RequestMapping("/oauth/user")
    public Principal self(HttpServletRequest request, Principal principal, Authentication authentication) {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>");
        log.info(request.getHeader(HttpHeaders.AUTHORIZATION));
        Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();
        Enumeration<String> attributeNames = request.getSession().getAttributeNames();
        while (attributeNames.hasMoreElements()) {
        log.info(attributeNames.nextElement());

        }
        if (principal != null) {
        log.info(principal.toString());

        }
        if (authentication != null) {
        log.info(authentication.toString());

        }
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>");
        return principal;
    }
}
