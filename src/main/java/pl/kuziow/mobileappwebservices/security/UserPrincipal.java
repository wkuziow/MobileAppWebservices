package pl.kuziow.mobileappwebservices.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.kuziow.mobileappwebservices.io.entity.UserEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserPrincipal implements UserDetails {
    private static final long serialVersionUID = 6654245944612188168L;

    UserEntity userEntity;

    public UserPrincipal(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        return null;
    }

    @Override
    public String getPassword() {
        return this.userEntity.getEncryptedPassword();
    }

    @Override
    public String getUsername() {
        return this.userEntity.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.userEntity.getEmailVerificationStatus();
    }
}
