package com.example.uac.security.support;

import com.example.uac.security.UserPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

import java.util.Arrays;

/**\
 * 获取用户
 */
public class DefaultUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!"test".equals(username)) throw new UsernameNotFoundException(username + "is null");
        String encode = PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("123456");
        return new UserPrincipal("test", encode, true, Arrays.asList(new SimpleGrantedAuthority("ROLE_LOGIN")));

    }
}
