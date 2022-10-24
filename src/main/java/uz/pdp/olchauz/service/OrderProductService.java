package uz.pdp.olchauz.service;

import org.springframework.stereotype.Service;
import uz.pdp.olchauz.model.dto.receive.OrderToCardDto;
import uz.pdp.olchauz.model.dto.receive.StringDto;
import uz.pdp.olchauz.model.entity.OrderProductEntity;
import uz.pdp.olchauz.model.entity.UserEntity;
import uz.pdp.olchauz.model.repository.OrderProductRepository;
import uz.pdp.olchauz.model.repository.ProductRepository;
import uz.pdp.olchauz.model.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OrderProductService {

    private final OrderProductRepository orderProductRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public OrderProductService(OrderProductRepository orderProductRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.orderProductRepository = orderProductRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public void addOrderToCard(
            final Optional<OrderToCardDto> order) {
        orderProductRepository.save(
                new OrderProductEntity(
                        userRepository.findByUsername(order.get().getUsername()).get(),
                        productRepository.findById(Long.valueOf(order.get().getProductId())).get(),
                        order.get().getCount()
                ));
    }

    public void addOrderToBuyed(
            final Optional<StringDto> id) {
        for (StringDto stringDto : id.stream().toList()) {
            OrderProductEntity order2 = orderProductRepository.findById(Long.parseLong(stringDto.getName())).get();
            order2.setIsBuyed(true);
            orderProductRepository.save(order2);
        }
    }

    public List<OrderProductEntity> getCarts(Optional<StringDto> stringDto) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(stringDto.get().getName());
        return orderProductRepository.findByUserEntityIdAndIsBuyed(userEntity.get().getId(), false);
    }

    public String deleteBasket(Optional<StringDto> stringDto) {
        Long id = orderProductRepository.findById(Long.parseLong(stringDto.orElseThrow().getId())).get().getUserEntity().getId();
        orderProductRepository.deleteById(Long.parseLong(stringDto.orElseThrow().getId()));
        return String.valueOf(id);
    }

    public String plusBasket(Optional<StringDto> stringDto) {
        OrderProductEntity order = orderProductRepository.findById(Long.parseLong(stringDto.orElseThrow().getId())).get();
        order.setCount(order.getCount() + 1);
        orderProductRepository.save(order);
        return String.valueOf(order.getUserEntity().getId());
    }

    public String minusBasket(Optional<StringDto> stringDto) {
        OrderProductEntity order = orderProductRepository.findById(Long.parseLong(stringDto.orElseThrow().getId())).get();
        order.setCount(order.getCount() > 1 ? order.getCount() - 1 : 1);
        orderProductRepository.save(order);
        return String.valueOf(order.getUserEntity().getId());
    }
}
