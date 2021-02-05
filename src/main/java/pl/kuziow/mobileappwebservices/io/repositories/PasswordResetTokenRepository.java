package pl.kuziow.mobileappwebservices.io.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.kuziow.mobileappwebservices.io.entity.PasswordResetTokenEntity;


public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetTokenEntity, Long> {
}
