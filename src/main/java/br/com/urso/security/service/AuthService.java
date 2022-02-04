package br.com.urso.security.service;

import br.com.urso.security.jwt.JwtUtils;
import br.com.urso.security.oauth2.entity.AuthProvider;
import br.com.urso.security.payload.request.LoginRequest;
import br.com.urso.security.payload.request.Oauth2Request;
import br.com.urso.security.payload.response.AuthResponse;
import br.com.urso.security.payload.response.JwtResponse;
import br.com.urso.security.userdetails.UserDetailsImpl;
import br.com.urso.user.entity.User;
import br.com.urso.user.exception.UserNotFoundException;
import br.com.urso.user.repository.UserRepository;
import br.com.urso.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;


    public JwtResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return (new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));

    }


    public JwtResponse loginOauth2(Oauth2Request request) {
        try {
            userService.getUserByEmail(request.getUsername());
            return login(userToLoginRequest(request));
        } catch (UserNotFoundException e) {
            var u = createUserOauth(request);
            userRepository.save(u);
            return login(userToLoginRequest(request));
        }
    }

    private LoginRequest userToLoginRequest(Oauth2Request r) {
        var login = new LoginRequest();
        login.setPassword(r.getPassword());
        login.setUsername(r.getUsername());

        return login;
    }

    private User createUserOauth(Oauth2Request request) {
        User user = new User();
        Random rand = new Random();

        user.setProvider(request.getProvider());
        user.setProviderId(request.getIdProvider());
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        String randomPass = String.valueOf(rand.nextInt(1000) * 137);
        request.setPassword(randomPass);
        user.setPassword(encoder.encode(randomPass));
        user.setEmail(request.getUsername());
        user.setBirth(LocalDate.now());

        return user;
    }

    public ResponseEntity createUser(User user) {
        return userService.createUser(user);
    }

    public ResponseEntity resetPassword(String email) {
        return userService.resetPassword(email);
    }
}
