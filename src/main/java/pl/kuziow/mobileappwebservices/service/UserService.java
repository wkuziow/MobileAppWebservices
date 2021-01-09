package pl.kuziow.mobileappwebservices.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import pl.kuziow.mobileappwebservices.shared.dto.UserDto;

public interface UserService extends UserDetailsService {

    UserDto createUser(UserDto user);
    UserDto getUser(String email);
    UserDto getUserByUserId(String userId);
    UserDto updateUser(String userId, UserDto user);
    void deleteUser(String userId);
}
