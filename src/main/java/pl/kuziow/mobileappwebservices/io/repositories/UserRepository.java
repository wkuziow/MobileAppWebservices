package pl.kuziow.mobileappwebservices.io.repositories;

import org.apache.catalina.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.kuziow.mobileappwebservices.io.entity.UserEntity;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {

    UserEntity findByEmail(String email);


    UserEntity findByUserId(String userId);

    UserEntity findUserByEmailVerificationToken(String token);
}
