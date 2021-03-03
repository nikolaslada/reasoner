package cz.nikolaslada.reasoner.mappers;

import cz.nikolaslada.reasoner.repository.model.User;
import cz.nikolaslada.reasoner.rest.swagger.domains.response.UserDetail;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDetail userModelToUserDetail(User user);

}
