package uz.pdp.olchauz.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.olchauz.model.dto.receive.PermissionDto;
import uz.pdp.olchauz.model.dto.receive.UserLogin;
import uz.pdp.olchauz.model.dto.receive.UserRegister;
import uz.pdp.olchauz.model.entity.RoleEntity;
import uz.pdp.olchauz.model.entity.RoleEnum;
import uz.pdp.olchauz.model.entity.UserEntity;
import uz.pdp.olchauz.model.repository.RoleRepository;
import uz.pdp.olchauz.model.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;


    public UserService(PasswordEncoder passwordEncoder, RoleRepository roleRepository, UserRepository userRepository, ObjectMapper objectMapper) {
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
    }

    public List<UserEntity> getUsers(Integer pageSize) {
        Pageable page = PageRequest.of(pageSize - 1, 6, Sort.by("id").descending());
        return userRepository.findAll(page).stream().toList();
    }

    public void addSuperAdmin() {
        UserEntity user = new UserEntity();
        user.setFullName("Super admin");
        user.setUsername("root");
        user.setPassword(passwordEncoder.encode("root"));
        user.setActive(true);
        user.setRoleEntityList(List.of(roleRepository.findByAuthority(RoleEnum.SUPER_ADMIN.name()).get()));
        userRepository.save(user);
    }

    public boolean addPermission(final PermissionDto permissionDto) throws JsonProcessingException {
        Optional<UserEntity> optionalUserEntity = userRepository.findByUsername(permissionDto.getUsername());

        if (optionalUserEntity.isPresent()) return false;

        UserEntity user = new UserEntity();
        user.setFullName(permissionDto.getFullName());
        user.setUsername(permissionDto.getUsername());
        user.setPassword(passwordEncoder.encode(permissionDto.getPassword()));
        user.setRoleEntityList(roleRepository.findByAuthority(permissionDto.getRole()).stream().toList());
        user.setPermissions(objectMapper.writeValueAsString(permissionDto.getPermissions()));
        user.setActive(true);
        userRepository.save(user);

        return true;
    }

    public boolean login(Optional<UserLogin> userLogin) {
        if (userLogin.isPresent()) {
            if (userRepository.findByUsername(userLogin.get().getUsername()).isPresent()) {
                UserEntity user = userRepository.findByUsername(userLogin.get().getUsername()).get();
                return passwordEncoder.matches(userLogin.get().getPassword(), user.getPassword());
            }
        }
        return false;
    }

    public boolean register(Optional<UserRegister> userRegister) {

        if (userRepository.findByUsername(userRegister.get().getUsername()).isPresent()) {
            return false;
        }

        ArrayList<RoleEntity> roleEntities = new ArrayList<>();
        roleEntities.add(new RoleEntity(RoleEnum.USER.name()));
        userRepository.save(new UserEntity(
                userRegister.get().getFullName(),
                userRegister.get().getUsername(),
                passwordEncoder.encode(userRegister.get().getPassword()),
                roleEntities,
                true
        ));

        return true;

    }

}
