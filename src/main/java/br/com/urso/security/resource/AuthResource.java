package br.com.urso.security.resource;

import br.com.urso.security.payload.request.LoginRequest;
import br.com.urso.security.payload.response.JwtResponse;
import br.com.urso.security.service.AuthService;
import br.com.urso.user.entity.User;
import br.com.urso.user.vo.UserVO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthResource {
    @Autowired
    private AuthService authService;

    @PostMapping(value = "/login", produces="application/json")
    @ApiOperation(value = "Login do usuario mediante email e senha", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Json Com as permissões e token", response = JwtResponse.class),
            @ApiResponse(code = 404, message = "Usuários não existente"),
            @ApiResponse(code = 500, message = "Erro interno") })
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping(value = "/create", consumes={"application/json"})
    @ApiOperation(value = "Cria um novo usuário", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Usuário criado com sucesso", response = UserVO.class, responseContainer = "Users"),
            @ApiResponse(code = 403, message = "Usuário já cadastrado"),
            @ApiResponse(code = 500, message = "Erro interno") })
    public ResponseEntity create(@Valid @RequestBody User user) {
        return ResponseEntity.ok(authService.createUser(user));
    }

    //reset de senha
    //sign up
    //oauth2

}
