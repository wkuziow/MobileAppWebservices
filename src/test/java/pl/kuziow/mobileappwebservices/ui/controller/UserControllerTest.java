package pl.kuziow.mobileappwebservices.ui.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.kuziow.mobileappwebservices.service.impl.UserServiceImpl;
import pl.kuziow.mobileappwebservices.shared.dto.AddressDTO;
import pl.kuziow.mobileappwebservices.shared.dto.UserDto;
import pl.kuziow.mobileappwebservices.ui.model.response.UserRest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserServiceImpl userService;

    UserDto userDto;
    final String USER_ID = "dwef84jhhqu2hskw";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

    userDto = new UserDto();
    userDto.setFirstName("Wojtek");
    userDto.setLastName("Kuziw");
    userDto.setEmail("test2@test.com");
    userDto.setEmailVerificationStatus(Boolean.FALSE);
    userDto.setEmailVerificationToken(null);
    userDto.setUserId(USER_ID);
    userDto.setAddresses(getAddressesDto());
    userDto.setEncryptedPassword("dfewf3fwsdc");
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

    @Test
    void getUser() {
        when(userService.getUserByUserId(anyString())).thenReturn(userDto);

        UserRest userRest = userController.getUser(USER_ID);
        assertNotNull(userRest);
        assertEquals(USER_ID, userRest.getUserId());
        assertEquals(userDto.getFirstName(), userRest.getFirstName());
        assertTrue(userDto.getAddresses().size() == userRest.getAddresses().size());
    }
}