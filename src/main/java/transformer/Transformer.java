package transformer;

import dto.UserDto;
import entity.User;

public class Transformer {
    public static UserDto toDto(User user) {
        return UserDto.builder().id(user.getId()).name(user.getName()).build();
    }

    public static User toEntity(UserDto user) {
        return User.builder().id(user.getId()).name(user.getName()).build();
    }
}
