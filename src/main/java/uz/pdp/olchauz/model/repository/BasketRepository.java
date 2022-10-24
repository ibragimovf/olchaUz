package uz.pdp.olchauz.model.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.olchauz.model.entity.BasketEntity;

public interface BasketRepository extends JpaRepository<BasketEntity, Long> {

}
