package uz.pdp.olchauz.config;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import uz.pdp.olchauz.exception.UserNotFoundException;
import uz.pdp.olchauz.model.entity.UserEntity;

@Component
public class CurrentUserConfig {

    public UserEntity getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return (UserEntity) principal;
        } else {
            throw new UserNotFoundException("user not found");
        }
    }
}
