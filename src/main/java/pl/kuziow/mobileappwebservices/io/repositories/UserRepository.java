package pl.kuziow.mobileappwebservices.io.repositories;

import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
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

    @Query(value = "select * from Users u where first_name LIKE %:keyword% or last_name LIKE %:keyword%", nativeQuery = true)
    List<UserEntity> findUserByKeyword(@Param("keyword") String keyword);

    @Query(value = "select u.first_name, u.last_name from Users u where u.first_name LIKE %:keyword% or u.last_name LIKE %:keyword%", nativeQuery = true)
    List<Object[]> findUserFirstNameAndLastNameByKeyword(@Param("keyword") String keyword);

    @Transactional
    @Modifying
    @Query(value = "update users u set u.EMAIL_VERIFICATION_STATUS=:emailVerificationStatus where u.user_id=:userId", nativeQuery = true)
    void updateUserEmailVerificationStatus(@Param("emailVerificationStatus") boolean emailVerificationStatus,
                                           @Param("userId") String userId);

    @Query("select user from UserEntity user where user.userId =: userId")
    UserEntity findUserEntitybyUserId(@Param("userId") String userId);

    @Query("select user.firstName, user.lastName from UserEntity user where user.userId =:userId")
    List<Object[]> getUserEntityFullNameById(@Param("userId") String userId);

    @Modifying
    @Transactional
    @Query("update UserEntity u set u.emailVerificationStatus =:emailVerificationStatus where u.userId=:userId")
    void updateUserEntityEmailVerificationStatus(
            @Param("emailVerificationStatus") boolean emailVerificationStatus,
            @Param("userId") String userId);
}
