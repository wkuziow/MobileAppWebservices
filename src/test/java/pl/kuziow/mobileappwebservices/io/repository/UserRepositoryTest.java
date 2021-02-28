package pl.kuziow.mobileappwebservices.io.repository;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.kuziow.mobileappwebservices.io.entity.AddressEntity;
import pl.kuziow.mobileappwebservices.io.entity.UserEntity;
import pl.kuziow.mobileappwebservices.io.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;
    static boolean recordsCreated = false;


    @BeforeEach
    void setUp() throws Exception {
        if (!recordsCreated) createRecords();
    }

    private void createRecords() {
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName("Serg");
        userEntity.setLastName("KGO");
        userEntity.setUserId("1a2bc3def");
        userEntity.setEncryptedPassword("xxx");
        userEntity.setEmail("test@test.com");
        userEntity.setEmailVerificationStatus(true);

        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setType("shipping");
        addressEntity.setAddressId("a56s78s99");
        addressEntity.setCity("Warsaw");
        addressEntity.setCountry("Poland");
        addressEntity.setPostalCode("03731");
        addressEntity.setStreetName("Targowa 4 10");

        List<AddressEntity> addresses = new ArrayList<>();
        addresses.add(addressEntity);
        userEntity.setAddresses(addresses);

        userRepository.save(userEntity);

        UserEntity userEntity2 = new UserEntity();
        userEntity2.setFirstName("Serg");
        userEntity2.setLastName("KGO");
        userEntity2.setUserId("1a2bc3def222222");
        userEntity2.setEncryptedPassword("xxx");
        userEntity2.setEmail("test@test.com");
        userEntity2.setEmailVerificationStatus(true);

        AddressEntity addressEntity2 = new AddressEntity();
        addressEntity2.setType("shipping");
        addressEntity2.setAddressId("a56s78s9922222");
        addressEntity2.setCity("Warsaw");
        addressEntity2.setCountry("Poland");
        addressEntity2.setPostalCode("03731");
        addressEntity2.setStreetName("Targowa 4 10");

        List<AddressEntity> addresses2 = new ArrayList<>();
        addresses2.add(addressEntity2);
        userEntity2.setAddresses(addresses2);

        userRepository.save(userEntity2);
        recordsCreated = true;
    }

    @Test
    final void testGetVerifiedUsers() {
        Pageable pageableRequest = PageRequest.of(0, 2);
        Page<UserEntity> pages = userRepository.findAllUsersWithConfirmedEmailAddresses(pageableRequest);
        assertNotNull(pages);

        List<UserEntity> userEntities = pages.getContent();
        assertNotNull(userEntities);
        assertTrue(userEntities.size() == 2);
    }

    @Test
    final void testFindUserByFirstName() {
        String firstName = "Serg";
        List<UserEntity> users = userRepository.findUserByFirstName(firstName);
        assertNotNull(users);
        assertTrue(users.size() == 8);

        UserEntity user = users.get(0);
        assertTrue(user.getFirstName().equals(firstName));
    }

    @Test
    final void testFindUserByLastName() {
        String lastName = "KGO";
        List<UserEntity> users = userRepository.findUserByLastName(lastName);
        assertNotNull(users);
        assertTrue(users.size() == 10);

        UserEntity user = users.get(0);
        assertTrue(user.getLastName().equals(lastName));
    }

}
