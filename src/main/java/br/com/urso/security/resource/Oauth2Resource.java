package br.com.urso.security.resource;

import br.com.urso.security.jwt.JwtUtils;
import br.com.urso.security.payload.request.LoginRequest;
import br.com.urso.security.payload.request.Oauth2Request;
import br.com.urso.security.payload.response.AuthResponse;
import br.com.urso.security.payload.response.JwtResponse;
import br.com.urso.security.service.AuthService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping(value = "/oauth2")
public class Oauth2Resource {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping(value = "/authenticate", produces="application/json")
    @ApiOperation(value = "Login ou criação do usuario mediante oauth", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Json Com as permissões e token", response = JwtResponse.class),
            @ApiResponse(code = 404, message = "Usuários não existente"),
            @ApiResponse(code = 500, message = "Erro interno") })
    public ResponseEntity<JwtResponse> oauth(@RequestBody Oauth2Request request){
        return ResponseEntity.ok().body(authService.loginOauth2(request));
    }
}
