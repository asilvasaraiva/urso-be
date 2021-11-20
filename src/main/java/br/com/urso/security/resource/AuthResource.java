package br.com.urso.security.resource;

import br.com.urso.security.payload.request.LoginRequest;
import br.com.urso.security.payload.response.JwtResponse;
import br.com.urso.security.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthResource {
    @Autowired
    private AuthService authService;

    @PostMapping(value = "/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(authService.login(loginRequest));
    }

}
