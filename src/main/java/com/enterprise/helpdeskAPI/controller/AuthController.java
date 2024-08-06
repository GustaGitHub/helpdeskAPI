package com.enterprise.helpdeskAPI.controller;

import com.enterprise.helpdeskAPI.DTO.SignInDTO;
import com.enterprise.helpdeskAPI.DTO.SignUpDTO;
import com.enterprise.helpdeskAPI.security.TokenService;
import com.enterprise.helpdeskAPI.security.UserSecurity;
import com.enterprise.helpdeskAPI.service.UserService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager _authenticationManager;

    @Autowired
    private TokenService _tokenService;

    @Autowired
    private UserService _userService;

    @PostMapping("/sign-in")
    public ResponseEntity SignIn(@RequestBody SignInDTO login){

        try {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword());

            Authentication authentication = _authenticationManager
                    .authenticate(usernamePasswordAuthenticationToken);

            UserSecurity user = (UserSecurity) authentication.getPrincipal();

            _userService.updateLastLoginUser(user.getId());

            return new ResponseEntity(_tokenService.generateToken(user), HttpStatus.OK);
        }catch (BadCredentialsException err){
            return new ResponseEntity(err.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (Exception err){
            return new ResponseEntity(err.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/sign-up")
    public ResponseEntity SignUp(@RequestBody SignUpDTO signUp){
        try {
            return new ResponseEntity(_userService.createUser(signUp), HttpStatus.CREATED);
        } catch (BadRequestException err){
            return new ResponseEntity(err.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception err) {
            return new ResponseEntity(err.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
