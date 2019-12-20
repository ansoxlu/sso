package com.example.uac.security.support;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 配置 URL 的需要角色, 参考 org.springframework.security.web.access.intercept.DefaultFilterInvocationSecurityMetadataSource
 */
public class UrlFilterSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    /**
     * 返回当前 URL 需要的角色
     */
    @Override
    public Set<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        FilterInvocation fi = (FilterInvocation) object;
        HttpServletRequest request = fi.getRequest();
        String url = fi.getRequestUrl();
        String method = fi.getRequest().getMethod();

        // 遍历我们初始化的权限数据，找到对应的url对应的权限
        // Lookup your database (or other source) using this information and populate the
        // list of attributes (这里初始话你的权限数据)
        Set<ConfigAttribute> attributes = new HashSet<>();
        if (!url.startsWith("/oauth")) {
            SecurityConfig config = new SecurityConfig("ROLE_ROOT");
            attributes.add(config);

        }


//        for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : requestMap
//                .entrySet()) {
//            if (entry.getKey().matches(request)) {
//                return entry.getValue();
//            }
//        }
        return attributes;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
