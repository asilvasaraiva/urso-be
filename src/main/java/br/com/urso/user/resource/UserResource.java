package br.com.urso.user.resource;

import br.com.urso.user.exception.UserNotFoundException;
import br.com.urso.user.entity.User;
import br.com.urso.user.entity.UserVO;
import br.com.urso.user.service.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserResource {

    private final UserService userService;

    @Autowired
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/", produces="application/json")
    @ApiOperation(value = "Recupera a lista completa de usuários", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista retornada com sucesso", response = User.class, responseContainer = "Users"),
            @ApiResponse(code = 404, message = "Lista de usuários não existente"),
            @ApiResponse(code = 500, message = "Erro interno") })
    public ResponseEntity<List<UserVO>> listaOfUsers(){
        return ResponseEntity.ok(userService.listUsers());
    }

    @GetMapping(value = "/{idUser}", produces="application/json")
    @ApiOperation(value = "Recupera um usuário específico por ID", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Usuário retornado com sucesso", response = UserVO.class, responseContainer = "Users"),
            @ApiResponse(code = 404, message = "Usuário não encontrado"),
            @ApiResponse(code = 500, message = "Erro interno") })
    public ResponseEntity<User> userById(@PathVariable("idUser") Long idUser) throws UserNotFoundException {
        return ResponseEntity.ok(userService.getUserById(idUser));
    }

    @PostMapping(value = "/create", consumes={"application/json"})
    @ApiOperation(value = "Cria um novo usuário", response = ResponseEntity.class)
    public ResponseEntity create(@Valid @RequestBody User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @PutMapping("/{idUser}/update")
    @ApiOperation(value = "Atualiza um usuário específico por ID", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Usuário atualizado com sucesso", response = UserVO.class, responseContainer = "Users"),
            @ApiResponse(code = 404, message = "Usuário não encontrado"),
            @ApiResponse(code = 500, message = "Erro interno") })
    public ResponseEntity<UserVO> updateUser(@PathVariable("idUser") Long idUser,@RequestBody UserVO userVO) throws UserNotFoundException {
        return ResponseEntity.ok(userService.update(idUser,userVO));
    }

    @GetMapping(value = "/{idUser}/admin", produces="application/json")
    @ApiOperation(value = "Altera o status de adminstrador", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Status do usuário alterado com sucesso", response = UserVO.class, responseContainer = "Users"),
            @ApiResponse(code = 404, message = "Usuário não encontrado"),
            @ApiResponse(code = 500, message = "Erro interno") })
    public ResponseEntity changeToAdmin (@PathVariable("idUser") Long idUser) throws UserNotFoundException {
        return ResponseEntity.ok(userService.changeStatusAdmin(idUser));
    }

    @GetMapping(value = "/admins", produces="application/json")
    @ApiOperation(value = "Retorna lista de Administradores", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Status do usuário alterado com sucesso", response = UserVO.class, responseContainer = "Users"),
            @ApiResponse(code = 404, message = "Usuário não encontrado"),
            @ApiResponse(code = 500, message = "Erro interno") })
    public ResponseEntity getListOfAdminsByStatus () throws UserNotFoundException {
        return ResponseEntity.ok(userService.getAdminUsers(true));
    }

}

