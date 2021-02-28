package pl.kuziow.mobileappwebservices.io.repositories;

import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.kuziow.mobileappwebservices.io.entity.UserEntity;

import java.util.List;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {

    UserEntity findByEmail(String email);


    UserEntity findByUserId(String userId);

    UserEntity findUserByEmailVerificationToken(String token);

    @Query(value = "select * from Users u where u.EMAIL_VERIFICATION_STATUS = 'true'",
            countQuery = "select count(*) from Users u where u.EMAIL_VERIFICATION_STATUS = 'true'",
            nativeQuery = true)
    Page<UserEntity> findAllUsersWithConfirmedEmailAddresses(Pageable pageableRequest);

    @Query(value = "select * from Users u where u.first_name = ?1", nativeQuery = true)
    List<UserEntity> findUserByFirstName(String firstName);

    @Query(value = "select * from Users u where u.last_name = :lastName", nativeQuery = true)
    List<UserEntity> findUserByLastName(@Param("lastName") String lastName);
}
