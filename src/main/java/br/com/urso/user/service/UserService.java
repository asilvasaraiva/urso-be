package br.com.urso.user.service;

import br.com.urso.user.entity.User;
import br.com.urso.user.entity.UserReview;
import br.com.urso.user.exception.DataIntegrityException;
import br.com.urso.user.exception.UserNotFoundException;
import br.com.urso.user.vo.UserVO;
import br.com.urso.user.mapper.UserMapper;
import br.com.urso.user.repository.UserRepository;
import br.com.urso.user.repository.UserReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserReviewRepository userReviewRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserReviewRepository userReviewRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userReviewRepository = userReviewRepository;
        this.userMapper = userMapper;
    }

    //---SECTION OF USER ----///

    public List<UserVO> listUsers(){
        List<User> users = userRepository.findAll();
        return  users.stream().map(u ->userMapper.toUserVo(u)).collect(Collectors.toList());
    }

    public User getUserById(Long id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(()-> new UserNotFoundException("User: "+id +" not found in database"));
    }

    public ResponseEntity createUser(User user ) {
        if(userRepository.findByEmail(user.getEmail())==null){
            return ResponseEntity.ok().body(userRepository.save(user));
        }else {
            return ResponseEntity.badRequest().body(HttpStatus.FORBIDDEN);
        }
    }

    public UserVO update(Long idUser, UserVO userVO ) throws UserNotFoundException {
        userVO.setIdUser(idUser);
        User user = getUserById(idUser);
         userRepository.save(user.merge(userVO));
        return userMapper.toUserVo(user);
    }

    public ResponseEntity deleteUser(Long id){
        getUserById(id);
        try {
            userRepository.deleteById(id);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        }catch(DataIntegrityViolationException e){
            throw new DataIntegrityException("Não foi possível excluir, pois o usuário não existe");
        }
    }


    //-----ADMIN SECTION----//

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

    //-----REVIEW----//

    public UserReview createReaview(UserReview userReview, Long idUser, Long idReceiver){
        User sender = getUserById(idUser);
        User receiver = getUserById(idReceiver);
        userReview.setUserSender(sender);
        userReview.setUserReceiver(receiver);

        userReview =  userReviewRepository.save(userReview);

        receiver.addReview(userReview);

        userRepository.save(receiver);

        return userReview;
    }

    public List<UserReview> getReviews(Long idUser){
        User user = getUserById(idUser);
        return user.getReviews();
    }

    public List<UserReview> reviewsAcceptedOrReject(Long idUser, int isAccept){
        User user = getUserById(idUser);
        List<UserReview> listAccepted;
        if(isAccept==1){
            listAccepted = user.getReviews().stream().filter(u->u.isAccepted()).collect(Collectors.toList());
            return listAccepted;
        }else{
            listAccepted = user.getReviews().stream().filter(u->!u.isAccepted()).collect(Collectors.toList());
            return listAccepted;
        }
    }

    public UserReview AcceptOrRejectReview(Long idUser, Long idReview, int isAccept){
        User user = getUserById(idUser);

        List<UserReview> reviewsToAccept = user.getReviews().stream().filter(ur-> !ur.isVisualized()).collect(Collectors.toList());
        UserReview review = null;
        for(UserReview ur : reviewsToAccept){
            if(ur.getIdReview()==idReview){
                review = ur;
            }
        }
        if(review!=null && !review.isVisualized()){
            if(isAccept==1){
                review.setAccepted(true);
            }else{
                review.setAccepted(false);
            }
            review.setVisualized(true);
            return userReviewRepository.save(review);
        }
        return null;

    }

}
