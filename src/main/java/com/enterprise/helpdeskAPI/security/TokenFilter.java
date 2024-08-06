package com.enterprise.helpdeskAPI.security;

import com.enterprise.helpdeskAPI.entity.User;
import com.enterprise.helpdeskAPI.enums.ProfileEnum;
import com.enterprise.helpdeskAPI.repository.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Component
public class TokenFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService _tokenService;

    @Autowired
    private IUserRepository _userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if(request.getHeader("Authorization") != null){

            String token = request.getHeader("Authorization").split(" ")[1];
            String email = _tokenService.getSubject(token);

            User user = _userService.findByEmail(email);
            UserSecurity userSecurity = new UserSecurity(user.getId(),
                                                         user.getUsername(),
                                                         user.getPassword(),
                                                        setRoleUser(user.getProfile()));

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(userSecurity, null, userSecurity.getAuthorities());

            usernamePasswordAuthenticationToken
                    .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        }

        filterChain.doFilter(request, response);
    }

    public Collection<? extends GrantedAuthority> setRoleUser(ProfileEnum profileEnumUser){
        String role = "";

        switch (profileEnumUser){
            case Administrator ->  role = "ROLE_ADMIN";
            case Support -> role = "ROLE_SUPPORT";
            case Client -> role = "ROLE_CLIENT";
            default -> role = null;
        }

        return List.of(new SimpleGrantedAuthority(role));

    }
}
