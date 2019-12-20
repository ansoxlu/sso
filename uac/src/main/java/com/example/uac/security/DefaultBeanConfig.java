package com.example.uac.security;

import com.example.uac.security.handle.DefaultAccessDeniedHandler;
import com.example.uac.security.handle.DefaultAccessExceptionTranslator;
import com.example.uac.security.handle.DefaultAuthenticationEntryPoint;
import com.example.uac.security.support.DefaultUserDetailsService;
import com.example.uac.security.support.AccessRoleVoter;
import com.example.uac.security.support.DynamicAuthorizationInterceptor;
import com.example.uac.security.support.UrlFilterSecurityMetadataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Configuration
@Component
public class DefaultBeanConfig {
    @Bean
    @ConditionalOnMissingBean(WebResponseExceptionTranslator.class)
    public WebResponseExceptionTranslator webResponseExceptionTranslator() {
        return new DefaultAccessExceptionTranslator();
    }

    @Bean
    @ConditionalOnMissingBean(PasswordEncoder.class)
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    @ConditionalOnMissingBean(UserDetailsService.class)
    public UserDetailsService userDetailsService() {
        return new DefaultUserDetailsService();
    }

    @Bean
    @ConditionalOnMissingBean(AccessDeniedHandler.class)
    public AccessDeniedHandler accessDeniedHandler() {
        return new DefaultAccessDeniedHandler();
    }

    @Bean
    @ConditionalOnMissingBean(AuthenticationEntryPoint.class)
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new DefaultAuthenticationEntryPoint();
    }

    @Bean
    @ConditionalOnMissingBean(DynamicAuthorizationInterceptor.class)
    public DynamicAuthorizationInterceptor dynamicAuthorizationInterceptor() {
        DynamicAuthorizationInterceptor interceptor = new DynamicAuthorizationInterceptor();
        // 设置 Url 角色源
        interceptor.setSecurityMetadataSource(new UrlFilterSecurityMetadataSource());
        //设置认证决策管理器
        interceptor.setAccessDecisionManager(new AffirmativeBased(Arrays.asList(
                new AccessRoleVoter()
        )));
        return interceptor;
    }
}
