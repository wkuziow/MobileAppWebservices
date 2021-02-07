package pl.kuziow.mobileappwebservices.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.Assert.*;


import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.kuziow.mobileappwebservices.io.entity.UserEntity;
import pl.kuziow.mobileappwebservices.io.repositories.PasswordResetTokenRepository;
import pl.kuziow.mobileappwebservices.io.repositories.UserRepository;
import pl.kuziow.mobileappwebservices.shared.Utils;
import pl.kuziow.mobileappwebservices.shared.dto.UserDto;


import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    Utils utils;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;


    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setFirstName("Wojtek");
        userEntity.setUserId("dsf33f");
        userEntity.setEncryptedPassword("3efrth6hred");
        when(userRepository.findByEmail(anyString())).thenReturn(userEntity);

        UserDto userDto = userService.getUser("test@test.com");

        assertNotNull(userDto);

        assertEquals("Wojtek", userDto.getFirstName());
    }

    @Test
    void testGetUser_UserNameNotFoundException() {
        when(userRepository.findByEmail(anyString())).thenReturn(null);
        assertThrows(UsernameNotFoundException.class,
                () -> {
                    userService.getUser("test@test.com");
                }
        );
    }

    @Test
    void createUser() {
        when(userRepository.findByEmail(anyString())).thenReturn(null);
        when(utils.generateAddressId(anyInt())).thenReturn("giu76tgiuyg");
        when(utils.generateUserId(anyInt())).thenReturn("dwhuied66g");

    }
}