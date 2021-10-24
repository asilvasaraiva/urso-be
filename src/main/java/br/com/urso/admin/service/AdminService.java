package br.com.urso.admin.service;

import br.com.urso.admin.entity.AdminMessage;
import br.com.urso.admin.repository.AdminRepository;
import br.com.urso.user.entity.User;
import br.com.urso.user.mapper.UserMapper;
import br.com.urso.user.repository.UserRepository;
import br.com.urso.user.service.UserService;
import br.com.urso.user.vo.UserVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {
    private final UserService userService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AdminRepository adminRepository;

    public AdminService(UserService userService, UserRepository userRepository, UserMapper userMapper, AdminRepository adminRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.adminRepository = adminRepository;
    }

    @Transactional
    public UserVO changeStatusAdmin(Long idUser){
        User user = userService.getUserById(idUser);
        if(user.isAdmin()){
            user.setAdmin(false);
        }else{
            user.setAdmin(true);
        }
        userRepository.save(user);
        return userMapper.toUserVo(user);
    }

    @Transactional
    public List<UserVO> getAdminUsers(Boolean isAdmin){
        List<User> adminList = userRepository.findByIsAdmin(isAdmin);
        return adminList.stream().map(u ->userMapper.toUserVo(u)).collect(Collectors.toList());
    }

    @Transactional
    public List<AdminMessage> getMessages(){
        return adminRepository.findAll();
    }

    @Transactional
    public AdminMessage updateStatusMessage(AdminMessage msg){
       return adminRepository.save(msg);
    }
}
