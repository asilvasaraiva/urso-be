package br.com.urso.admin.resource;

import br.com.urso.admin.entity.AdminMessage;
import br.com.urso.admin.service.AdminService;
import br.com.urso.user.exception.UserNotFoundException;
import br.com.urso.user.vo.UserVO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminResource {
    @Autowired
    private final AdminService adminService;

    public AdminResource(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping(value = "/list", produces="application/json")
    @ApiOperation(value = "Retorna lista de Administradores", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Status do usuário alterado com sucesso", response = UserVO.class, responseContainer = "Users"),
            @ApiResponse(code = 404, message = "Usuário não encontrado"),
            @ApiResponse(code = 500, message = "Erro interno") })
    public ResponseEntity getListOfAdminsByStatus () throws UserNotFoundException {
        return ResponseEntity.ok(adminService.getAdminUsers(true));
    }

    @GetMapping("/messages")
    public ResponseEntity getMessages(){
    return ResponseEntity.ok(adminService.getMessages());
    }

    @PutMapping("/messages/{id}")
    public ResponseEntity getMessages(@RequestBody AdminMessage msg, @PathVariable Long id){
        return ResponseEntity.ok(adminService.updateStatusMessage(msg));
    }


    @GetMapping(value = "/{idUser}/admin", produces="application/json")
    @ApiOperation(value = "Altera o status de adminstrador", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Status do usuário alterado com sucesso", response = UserVO.class, responseContainer = "Users"),
            @ApiResponse(code = 404, message = "Usuário não encontrado"),
            @ApiResponse(code = 500, message = "Erro interno") })
    public ResponseEntity changeToAdmin (@PathVariable("idUser") Long idUser) throws UserNotFoundException {
        return ResponseEntity.ok(adminService.changeStatusAdmin(idUser));
    }

}
