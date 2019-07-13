package com.example.uac.security.support;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * 认证使用
 * security.oauth2.resource.user-info-uri=
 */
@RestController
public class PrincipalController {
    @RequestMapping("/oauth/user")
    public Principal user(Principal principal) {
        return principal;
    }
}
