package com.ebn.calendar.controller;

import com.ebn.calendar.model.dao.User;
import com.ebn.calendar.model.dto.request.SignInRequest;
import com.ebn.calendar.model.dto.request.SignUpRequest;
import com.ebn.calendar.model.dto.response.JwtResponse;
import com.ebn.calendar.model.dto.response.MessageResponse;
import com.ebn.calendar.service.AuthService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping(path="/auth", produces = "application/json")
public class AuthController {

    final AuthService authService;
    final ModelMapper modelMapper;

    @Autowired
    public AuthController(AuthService authService, ModelMapper modelMapper) {
        this.authService = authService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@Valid @RequestBody SignInRequest signInRequest) {
        User user = userFromRequest(signInRequest);

        String token= authService.authenticate(user);
        if(token==null){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: couldn't load user with given credentials"));
        }

        JwtResponse response=jwtResponseFromUser(user);
        response.setToken(token);
        return ResponseEntity.ok()
                .body(response);
    }


    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        User user = userFromRequest(signUpRequest);
        if (authService.readByUsername(user.getUsername()) != null) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: username is already taken"));
        }

        User result = authService.create(user);
        if(result==null){
            return ResponseEntity.internalServerError()
                    .body(new MessageResponse("Error: couldn't register user"));
        }
        return ResponseEntity.ok()
                .body(new MessageResponse("user registered successfully"));
    }

    //should be used only on SignUpRequest and SignInRequest
    private User userFromRequest(Object signUpAndInRequest) {
        return modelMapper.map(signUpAndInRequest, User.class);
    }

    private JwtResponse jwtResponseFromUser(User user){return modelMapper.map(user, JwtResponse.class);}
}


