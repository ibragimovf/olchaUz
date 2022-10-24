package uz.pdp.olchauz.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class OrderProductEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "user_entity_id", nullable = false)
    private UserEntity userEntity;

    @ManyToOne()
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity productEntity;

    @Column(name = "count", nullable = false)
    private Integer count;

    @CreationTimestamp
    @Column(name = "createdDate", nullable = false)
    private Timestamp createdDate;

    @Column(name = "is_buyed")
    private Boolean isBuyed;


    public OrderProductEntity(UserEntity userEntity, ProductEntity productEntity, Integer count) {
        this.userEntity = userEntity;
        this.productEntity = productEntity;
        this.count = count;
        this.isBuyed = false;
    }
}
