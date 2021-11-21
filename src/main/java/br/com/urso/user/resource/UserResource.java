package br.com.urso.user.resource;

import br.com.urso.user.entity.UserReview;
import br.com.urso.user.exception.UserNotFoundException;
import br.com.urso.user.entity.User;
import br.com.urso.user.vo.UserReviewVO;
import br.com.urso.user.vo.UserVO;
import br.com.urso.user.service.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<UserVO> userById(@PathVariable("idUser") Long idUser) throws UserNotFoundException {
        return ResponseEntity.ok(userService.getUserVOById(idUser));
    }

    @PutMapping("/{idUser}/edit")
    @ApiOperation(value = "Atualiza um usuário específico por ID", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Usuário atualizado com sucesso", response = UserVO.class, responseContainer = "Users"),
            @ApiResponse(code = 404, message = "Usuário não encontrado"),
            @ApiResponse(code = 500, message = "Erro interno") })
    public ResponseEntity<UserVO> updateUser(@PathVariable("idUser") Long idUser,
                                             @RequestBody UserVO userVO) throws UserNotFoundException {
        return ResponseEntity.ok(userService.update(idUser,userVO));
    }

    @DeleteMapping(value = "/{idUser}/delete", produces="application/json")
    @ApiOperation(value = "Deleta um usuário específico por ID", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Usuário deletado com sucesso", response = UserVO.class, responseContainer = "Users"),
            @ApiResponse(code = 404, message = "Usuário não encontrado"),
            @ApiResponse(code = 500, message = "Erro interno") })
    public ResponseEntity deleteUser (@PathVariable("idUser") Long idUser){
        return ResponseEntity.ok(userService.deleteUser(idUser));
    }



    @PostMapping(value = "/{idUser}/reviews/create2/{idReceiver}", produces={"application/json"})
    @ApiOperation(value = "Cria um depoimento para um outro  usuário", response = UserReview.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Depoimento criado com sucesso", response = UserReviewVO.class, responseContainer = "Users"),
            @ApiResponse(code = 404, message = "Usuário destinatário  não encontrado"),
            @ApiResponse(code = 500, message = "Erro interno") })
    public ResponseEntity createReview(@Valid @RequestBody UserReview userReview,
                                       @PathVariable("idUser") Long idUser,
                                       @PathVariable("idReceiver") Long idReceiver) {
        return ResponseEntity.ok(userService.createReaview(userReview,idUser,idReceiver));
    }
    @GetMapping(value = "/{idUser}/reviews", produces={"application/json"})
    @ApiOperation(value = "Retorna a lista completa de depoimentos, tanto os aceitos quanto os recusados", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista retornada com sucesso", response = UserReviewVO.class, responseContainer = "Users"),
            @ApiResponse(code = 404, message = "Usuário não possui depoimentos"),
            @ApiResponse(code = 500, message = "Erro interno") })
    public ResponseEntity listOfReviewsToAccept(@PathVariable("idUser") Long idUser) {
        return ResponseEntity.ok(userService.getReviews(idUser));
    }


    @GetMapping(value = "/{idUser}/reviews/{accepted}", produces={"application/json"})
    @ApiOperation(value = "Retorna a lista de depoimentos filtrada. Accepted= 1 retorna lista dos depoimentos aceitos, qualquer outro a lista dos não aceitos", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista filtrada retornada sucesso", response = UserReviewVO.class, responseContainer = "Users"),
            @ApiResponse(code = 404, message = "Usuário destinatário  não encontrado"),
            @ApiResponse(code = 500, message = "Erro interno") })
    public ResponseEntity listOfReviewsToAccept(@PathVariable("idUser") Long idUser,
                                                @PathVariable("accepted") Integer accepted) {
        return ResponseEntity.ok(userService.reviewsAcceptedOrReject(idUser,accepted));
    }

    @GetMapping(value = "/{idUser}/reviews/{idReview}/accept/{accepted}", produces={"application/json"})
    @ApiOperation(value = "Aceita ou Rejeita um depoimento. Accepted = 1 aceita, qualquer outro rejeita ", response = HttpStatus.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Depoimento aceito ou rejeitado com sucesso", response = HttpStatus.class, responseContainer = "Users"),
            @ApiResponse(code = 403, message = "Depoimento ja Aceito ou Rejeitado"),
            @ApiResponse(code = 500, message = "Erro interno") })
    public ResponseEntity changeReviewStatus(@PathVariable("idUser") Long idUser,
                                             @PathVariable("idReview") Long idReview,
                                             @PathVariable("accepted") Integer accepted) {
        return ResponseEntity.ok(userService.AcceptOrRejectReview(idUser,idReview,accepted));
    }

}

