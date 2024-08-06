    package com.enterprise.helpdeskAPI.service;


import com.enterprise.helpdeskAPI.DTO.SignUpDTO;
import com.enterprise.helpdeskAPI.entity.User;
import com.enterprise.helpdeskAPI.exceptions.NotFoundException;
import com.enterprise.helpdeskAPI.repository.IUserRepository;
import com.enterprise.helpdeskAPI.security.SecurityConf;
import org.springframework.transaction.annotation.Transactional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private IUserRepository _userRepository;

    @Autowired
    private SecurityConf _securityConfig;

    @Transactional
    public User createUser(SignUpDTO signUp) throws Exception {
        boolean userAlreadyExists = _userRepository.findByEmail(signUp.getEmail().trim()) != null;

        if(userAlreadyExists)
            throw new BadRequestException("an account has already been created with this email");
        else{
            String passwordEncoded = _securityConfig.passwordEncoder().encode(signUp.getPassword());

            User newUser = User.builder()
                                .username(signUp.getUsername())
                                .email(signUp.getEmail().trim())
                                .password(passwordEncoded)
                                .profile(signUp.getProfile())
                                .createdAt(new Timestamp(System.currentTimeMillis()))
                                .build();

            return _userRepository.save(newUser);
        }
    }

    @Transactional
    public User updateLastLoginUser(Long id) throws Exception{
        Optional<User> userById = _userRepository.findById(id);

        if(userById.isPresent()){

            User user = userById.get();
            user.setLastLogin(new Timestamp(System.currentTimeMillis()));

            return _userRepository.save(user);
        }

       throw new NotFoundException("User not found");
    }

}
