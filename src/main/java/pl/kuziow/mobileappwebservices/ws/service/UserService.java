package pl.kuziow.mobileappwebservices.ws.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import pl.kuziow.mobileappwebservices.shared.dto.UserDto;

public interface UserService extends UserDetailsService {

    UserDto createUser(UserDto user);
}
