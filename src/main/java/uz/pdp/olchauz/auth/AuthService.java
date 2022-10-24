package uz.pdp.olchauz.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.pdp.olchauz.exception.UserNotFoundException;
import uz.pdp.olchauz.model.entity.UserEntity;
import uz.pdp.olchauz.model.repository.UserRepository;

import java.util.Optional;

@Service
public class AuthService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AuthService() {
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> optionalUserEntity = userRepository.findByUsername(username);
        if (optionalUserEntity.isEmpty())
            throw new UserNotFoundException(username + " not found");

        return optionalUserEntity.get();
    }
}
