package cz.nikolaslada.reasoner.mappers;

import cz.nikolaslada.reasoner.repository.model.User;
import cz.nikolaslada.reasoner.rest.swagger.domains.response.UserDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {ObjectIdToStringMapper.class, ObjectIdToDateMapper.class})
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "user.id", target = "createdAt")
    UserDetail userModelToUserDetail(User user);

}
