package br.com.urso.user.mapper;

import br.com.urso.user.entity.User;
import br.com.urso.user.entity.UserVO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UserVO userVO);

    UserVO toUserVo(User user);
}
