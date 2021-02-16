package pl.kuziow.mobileappwebservices.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.Assert.*;


import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.w3c.dom.stylesheets.LinkStyle;
import pl.kuziow.mobileappwebservices.exceptions.UserServiceException;
import pl.kuziow.mobileappwebservices.io.entity.AddressEntity;
import pl.kuziow.mobileappwebservices.io.entity.UserEntity;
import pl.kuziow.mobileappwebservices.io.repositories.PasswordResetTokenRepository;
import pl.kuziow.mobileappwebservices.io.repositories.UserRepository;
import pl.kuziow.mobileappwebservices.shared.AmazonSES;
import pl.kuziow.mobileappwebservices.shared.Utils;
import pl.kuziow.mobileappwebservices.shared.dto.AddressDTO;
import pl.kuziow.mobileappwebservices.shared.dto.UserDto;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    Utils utils;
    @Mock
    AmazonSES amazonSES;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    String userId = "dsf33f";

    String password = "3efrth6hred";

    UserEntity userEntity;


    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setFirstName("Wojtek");
        userEntity.setLastName("Kuziow");
        userEntity.setUserId(userId);
        userEntity.setEncryptedPassword(password);
        userEntity.setEmail("test@test.com");
        userEntity.setEmailVerificationToken("dwed73hdss8e");
        userEntity.setAddresses(getAddresssesEntity());
    }

    @Test
    void getUser() {

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
    final void testCreateUser_CreateUserServiceException(){
        when(userRepository.findByEmail(anyString())).thenReturn(userEntity);

        UserDto userDto = new UserDto();
        userDto.setAddresses(getAddressesDto());
        userDto.setFirstName("Wojtek");
        userDto.setLastName("Kuziow");
        userDto.setPassword("1dwedww");
        userDto.setEmail("test@test.com");

        assertThrows(UserServiceException.class,
                () -> {
                    userService.createUser(userDto);
                }
        );

    }

    @Test
    void createUser() {

        when(userRepository.findByEmail(anyString())).thenReturn(null);
        when(utils.generateAddressId(anyInt())).thenReturn("giu76tgiuyg");
        when(utils.generateUserId(anyInt())).thenReturn(userId);
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn(password);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        Mockito.doNothing().when(amazonSES).verifyEmail(any(UserDto.class));

        UserDto userDto = new UserDto();
        userDto.setAddresses(getAddressesDto());
        userDto.setFirstName("Wojtek");
        userDto.setLastName("Kuziow");
        userDto.setPassword("1dwedww");
        userDto.setEmail("test@test.com");

        UserDto storedUserDetail = userService.createUser(userDto);

        assertNotNull(storedUserDetail);
        assertEquals(userEntity.getFirstName(), storedUserDetail.getFirstName());
        assertEquals(userEntity.getLastName(), storedUserDetail.getLastName());
        assertNotNull(storedUserDetail.getUserId());
        assertEquals(storedUserDetail.getAddresses().size(), userEntity.getAddresses().size());

        verify(utils, times(2)).generateAddressId(30);
        verify(bCryptPasswordEncoder, times(1)).encode("1dwedww");
        verify(userRepository, times(1)).save(any(UserEntity.class));

    }

    private List<AddressDTO> getAddressesDto() {

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setType("shipping");
        addressDTO.setCity("Kielce");
        addressDTO.setCountry("Polska");
        addressDTO.setPostalCode("23333");
        addressDTO.setStreetName("Radiowa 1");

        AddressDTO billingAddressDTO = new AddressDTO();
        billingAddressDTO.setType("billing");
        billingAddressDTO.setCity("Kielce");
        billingAddressDTO.setCountry("Polska");
        billingAddressDTO.setPostalCode("23333");
        billingAddressDTO.setStreetName("Radiowa 1");

        List<AddressDTO> addresses = new ArrayList<>();
        addresses.add(addressDTO);
        addresses.add(billingAddressDTO);

        return addresses;
    }

    private List<AddressEntity> getAddresssesEntity() {
        List<AddressDTO> addresses = getAddressesDto();

        Type listType = new TypeToken<List<AddressEntity>>() {
        }.getType();

        return new ModelMapper().map(addresses, listType);

    }
}