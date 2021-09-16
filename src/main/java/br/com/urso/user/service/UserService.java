package br.com.urso.user.service;

import br.com.urso.user.entity.User;
import br.com.urso.exception.UserNotFoundException;
import br.com.urso.user.entity.UserVO;
import br.com.urso.user.mapper.UserMapper;
import br.com.urso.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserVO> listUsers(){
        List<User> users = userRepository.findAll();
        return  users.stream().map(u ->userMapper.toUserVo(u)).collect(Collectors.toList());
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
        userRepository.save(user);
        return userMapper.toUserVo(user);
    }

    public List<UserVO> getAdminUsers(Boolean isAdmin){
        List<User> adminList = userRepository.findByIsAdmin(isAdmin);
        return adminList.stream().map(u ->userMapper.toUserVo(u)).collect(Collectors.toList());
    }

}
