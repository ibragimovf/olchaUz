package uz.pdp.olchauz.model.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.olchauz.model.entity.OrderProductEntity;

import java.util.List;

public interface OrderProductRepository extends JpaRepository<OrderProductEntity, Long> {

    @Query(value = "select * from order_product_entity where is_buyed = true order by id  desc offset (?1-1)*5 limit ?2",
            nativeQuery = true)
    List<OrderProductEntity> getOrderList(int page, int size);


    @Query(value = "select * from order_product_entity where is_buyed = false and user_entity_id = ?1 order by id  desc",
            nativeQuery = true)
    List<OrderProductEntity> getBasketList(long userId);

    List<OrderProductEntity> findByUserEntityIdAndIsBuyed(Long userId, boolean isBuyed);

}
