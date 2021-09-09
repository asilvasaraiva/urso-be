package com.urso.user.resource;

import com.urso.exception.UserNotFoundException;
import com.urso.user.service.UserService;
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
    public ResponseEntity listUsers(){
        return ResponseEntity.ok(userService.listUsers());
    }

    @GetMapping("/{idUser}")
    public ResponseEntity userById(@PathVariable("idUser") Long idUser) throws UserNotFoundException {
        return ResponseEntity.ok(userService.getUserById(idUser));
    }
}
