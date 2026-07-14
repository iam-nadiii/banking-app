package com.banking.controller;



import jakarta.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.banking.model.Profile;
import com.banking.service.ProfileService;
import com.banking.service.UserService;
import com.banking.model.authentication.LoginDto;
import com.banking.model.authentication.LoginResponseDto;
import com.banking.model.authentication.RegisterUserDto;
import com.banking.model.User;
import com.banking.security.jwt.JWTFilter;
import com.banking.security.jwt.TokenProvider;

@RestController
@CrossOrigin
@PreAuthorize("permitAll()")
public class AuthenticationController {

    private final TokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final ProfileService profileService;

    public AuthenticationController(TokenProvider tokenProvider, AuthenticationManager authenticationManager, UserService userService, ProfileService profileService) {
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.profileService = profileService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginDto loginDto) {
        try
        {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = tokenProvider.createToken(authentication, false);

            User user = userService.getByUserName(loginDto.getUsername());

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
            return new ResponseEntity<>(new LoginResponseDto(jwt, user), httpHeaders, HttpStatus.OK);
        }
        catch (AuthenticationException e)
        {
            // bad username/password -> 401 (not a 500)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password.");
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<User> register(@Valid @RequestBody RegisterUserDto newUser) {

        boolean exists = userService.exists(newUser.getUsername());
        if (exists)
        {
            // duplicate username -> 400 (not a 500)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User Already Exists.");
        }

        // FIX: confirmPassword existed on the DTO but was never actually
        // checked against password — added here.
        if (!newUser.getPassword().equals(newUser.getConfirmPassword()))
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Passwords do not match.");
        }

        // FIX: role is no longer read from the client at all (removed from
        // RegisterUserDto). Every self-registration is USER — promoting to
        // ADMIN is a manual DB step, same as the rest of this project.
        User user = new User(null, newUser.getUsername(), newUser.getPassword(), "USER");
        user.setEmail(newUser.getEmail());
        user = userService.create(user);

        // create profile
        Profile profile = new Profile();
        profile.setUserId(user.getId());
        profileService.create(profile);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

}