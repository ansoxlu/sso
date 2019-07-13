package com.example.uac.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;

import java.util.Collection;

/**
 * 实现动态角色
 */
public class DynamicRoleVoter extends RoleVoter {
    @Override
    public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
        FilterInvocation invocation = (FilterInvocation) object;
        String method = invocation.getRequest().getMethod().toUpperCase();
        String url = invocation.getRequestUrl();

        if (authentication == null) {
            return ACCESS_DENIED;
        }
        int result = ACCESS_ABSTAIN;
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        for (ConfigAttribute attribute : attributes) {
            if (attribute.getAttribute() == null) {
                continue;
            }
            if (super.supports(attribute)) {
                result = ACCESS_DENIED;

                // Attempt to find a matching granted authority
                for (GrantedAuthority authority : authorities) {
                    if (attribute.getAttribute().equals(authority.getAuthority()) ||
                            matches(attribute, authority, method, HttpMethod.GET, "_GET") ||
                            matches(attribute, authority, method, HttpMethod.POST, "_SAVE") ||
                            matches(attribute, authority, method, HttpMethod.PUT, "_SAVE") ||
                            matches(attribute, authority, method, HttpMethod.DELETE, "_DELETE")
                    ) {
                        return ACCESS_GRANTED;
                    }
                }
            }
        }
        return result;
    }

    private boolean matches(ConfigAttribute attribute, GrantedAuthority authority, String method, HttpMethod httpMethod, String type) {
        return httpMethod.matches(method) && (attribute.getAttribute() + type).equals(authority.getAuthority());
    }
}
