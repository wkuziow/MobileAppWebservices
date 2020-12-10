package pl.kuziow.mobileappwebservices.ws.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kuziow.mobileappwebservices.UserRepository;
import pl.kuziow.mobileappwebservices.io.entity.UserEntity;
import pl.kuziow.mobileappwebservices.shared.dto.UserDto;
import pl.kuziow.mobileappwebservices.ws.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Override
    public UserDto createUser(UserDto user) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);

        userEntity.setEncryptedPassword("test");
        userEntity.setUserId("testUserId");
        UserEntity storedUserDetails = userRepository.save(userEntity);
        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(storedUserDetails, returnValue);

        return returnValue;
    }
}
