package br.com.urso.user.mapper;


import br.com.urso.user.entity.UserReview;
import br.com.urso.user.vo.UserReviewVO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserReviewMapper {


    UserReview toUser(UserReviewVO userReviewVO);

    UserReviewVO toUserVo(UserReview userReview);


}
