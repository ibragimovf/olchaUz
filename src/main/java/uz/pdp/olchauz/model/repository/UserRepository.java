package uz.pdp.olchauz.model.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.olchauz.model.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);

//    @Query(value = "SELECT * FROM user_entity OFFSET (?1 - 1) * 7 limit 7", nativeQuery = true)
//    Optional<UserEntity> getUserEntitiesPage(Integer pageSize);
}
