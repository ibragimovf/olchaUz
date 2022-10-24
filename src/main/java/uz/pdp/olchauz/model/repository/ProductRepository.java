package uz.pdp.olchauz.model.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.olchauz.model.entity.ProductEntity;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findByCategory_Id(Long aLong);
}
