package com.example.uac.security.support;

import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;

import java.util.Collection;

/**
 * 实现动态角色, 参考 org.springframework.security.access.vote.RoleVoter
 */
public class AccessRoleVoter implements AccessDecisionVoter<Object> {
    private String rolePrefix = "ROLE_";

    public String getRolePrefix() {
        return rolePrefix;
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        if ((attribute.getAttribute() != null)
                && attribute.getAttribute().startsWith(getRolePrefix())) {
            return true;
        } else {
            return false;
        }
    }

    public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
        FilterInvocation fi = (FilterInvocation) object;
        HttpMethod method = HttpMethod.resolve(fi.getRequest().getMethod().toUpperCase());
        String url = fi.getRequestUrl();

        if (authentication == null) {
            return ACCESS_DENIED;
        }
        int result = ACCESS_ABSTAIN;
        Collection<? extends GrantedAuthority> authorities = extractAuthorities(authentication);

        for (ConfigAttribute attribute : attributes) {
            String attr = attribute.getAttribute();
            if (attr == null) {
                continue;
            }
            if (supports(attribute)) {
                result = ACCESS_DENIED;

                // Attempt to find a matching granted authority
                for (GrantedAuthority authority : authorities) {
                    String auth = authority.getAuthority();
                    if (attr.equals(auth)) {
                        return ACCESS_GRANTED;
                    } else if (auth.endsWith("_")) {
                        switch (method) {
                            case GET:
                                if (attr.equals(auth + "_GET_")) return ACCESS_GRANTED;
                            case POST:
                                if (attr.equals(auth + "_SAVE_")) return ACCESS_GRANTED;
                            case PUT:
                                if (attr.equals(auth + "_SAVE_")) return ACCESS_GRANTED;
                            case DELETE:
                                if (attr.equals(auth + "_DELETE_")) return ACCESS_GRANTED;
                        }
                        return ACCESS_GRANTED;
                    }
                }
            }
        }
        return result;
    }

    @Override
    public boolean supports(Class clazz) {
        return true;
    }

    Collection<? extends GrantedAuthority> extractAuthorities(
            Authentication authentication) {
        return authentication.getAuthorities();
    }

    private boolean matches(ConfigAttribute attribute, GrantedAuthority authority, String method, HttpMethod httpMethod, String suffix) {
        return httpMethod.matches(method) && (attribute.getAttribute() + suffix).equals(authority.getAuthority());
    }
}
