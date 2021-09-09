package br.com.urso.user.resource;

import br.com.urso.exception.UserNotFoundException;
import br.com.urso.user.entity.UserVO;
import br.com.urso.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserResource {

    private final UserService userService;

    @Autowired
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    @ApiOperation(value = "Recupera loja específica de um determinado usuário", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de U", response = UserVO.class, responseContainer = "Users"),
            @ApiResponse(code = 404, message = "usuário não encontrado"),
            @ApiResponse(code = 404, message = "Loja não encontrada"),
            @ApiResponse(code = 500, message = "Erro interno") })
    public ResponseEntity listUsers(){
        return ResponseEntity.ok(userService.listUsers());
    }

    @GetMapping("/{idUser}")
    public ResponseEntity userById(@PathVariable("idUser") Long idUser) throws UserNotFoundException {
        return ResponseEntity.ok(userService.getUserById(idUser));
    }
}
