package uz.pdp.olchauz.model.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.olchauz.model.entity.RoleEntity;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByAuthority(String authority);
}
