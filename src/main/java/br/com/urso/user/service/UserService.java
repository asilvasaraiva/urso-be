package br.com.urso.user.service;

import br.com.urso.user.entity.User;
import br.com.urso.exception.UserNotFoundException;
import br.com.urso.user.entity.UserVO;
import br.com.urso.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> listUsers(){
        return userRepository.findAll();
    }

    public User getUserById(Long id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(()-> new UserNotFoundException("User: "+id +" not found in database"));
    }

    public UserVO changeStatusAdmin(Long idUser){
        User user = getUserById(idUser);
        if(user.isAdmin()){
            user.setAdmin(false);
        }else{
            user.setAdmin(true);
        }
        UserVO userVO =  UserVO.builder().build();
        userVO.setIdUser(user.getIdUser());
        userVO.setName(user.getName());
        userVO.setAdmin(user.isAdmin());
        userVO.setIdUser(user.getIdUser());
        return userVO;
    }

    public List<User> getAdminUsers(Boolean isAdmin){
        List<User> adminList = userRepository.findByIsAdmin(isAdmin);
        return adminList;
    }

}
