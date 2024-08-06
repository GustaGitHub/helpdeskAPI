package com.enterprise.helpdeskAPI.security;

import com.enterprise.helpdeskAPI.entity.User;
import com.enterprise.helpdeskAPI.enums.ProfileEnum;
import com.enterprise.helpdeskAPI.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class AutheticationService implements UserDetailsService {

    @Autowired
    private IUserRepository _userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User userByEmail = _userRepository.findByEmail(email);

        if(userByEmail.equals(null))
            throw new UsernameNotFoundException("User not found");
        else
            return new UserSecurity(
                    userByEmail.getId(),
                    userByEmail.getEmail(),
                    userByEmail.getPassword(),
                    setRoleUser(userByEmail.getProfile()));
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
