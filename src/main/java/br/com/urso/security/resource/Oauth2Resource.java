package br.com.urso.security.resource;

import br.com.urso.security.payload.request.LoginRequest;
import br.com.urso.security.payload.response.JwtResponse;
import br.com.urso.security.service.AuthService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/oauth2")
public class Oauth2Resource {

    @Autowired
    private AuthService authService;

    @PostMapping(value = "/login", produces="application/json")
    @ApiOperation(value = "Login do usuario mediante Terceiro", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Json Com as permissões e token", response = JwtResponse.class),
            @ApiResponse(code = 404, message = "Usuários não existente"),
            @ApiResponse(code = 500, message = "Erro interno") })
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(authService.login(loginRequest));
    }


    @GetMapping("/teste")
    public  String teste(){
        return "teste oauth2 funfou";
    }


//    @PostMapping("/signup")
//    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
//        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
//            throw new BadRequestException("Email address already in use.");
//        }
//
//        // Creating user's account
//        User user = new User();
//        user.setName(signUpRequest.getName());
//        user.setEmail(signUpRequest.getEmail());
//        user.setPassword(signUpRequest.getPassword());
//        user.setProvider(AuthProvider.local);
//
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//
//        User result = userRepository.save(user);
//
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentContextPath().path("/user/me")
//                .buildAndExpand(result.getId()).toUri();
//
//        return ResponseEntity.created(location)
//                .body(new ApiResponse(true, "User registered successfully@"));
//    }
}
