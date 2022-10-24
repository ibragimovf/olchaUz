package uz.pdp.olchauz.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.olchauz.model.dto.receive.OrderToCardDto;
import uz.pdp.olchauz.model.dto.receive.StringDto;
import uz.pdp.olchauz.model.dto.receive.UserLogin;
import uz.pdp.olchauz.model.dto.receive.UserRegister;
import uz.pdp.olchauz.model.entity.CategoryEntity;
import uz.pdp.olchauz.model.entity.OrderProductEntity;
import uz.pdp.olchauz.model.entity.ProductEntity;
import uz.pdp.olchauz.service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class RestApiController {

    private final UserService userService;
    private final CategoryService categoryService;
    private final ProductService productService;
    private final OrderProductService orderProductService;
    private final BasketService basketService;

    public RestApiController(UserService userService, CategoryService categoryService, ProductService productService, OrderProductService orderProductService, BasketService basketService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.productService = productService;
        this.orderProductService = orderProductService;
        this.basketService = basketService;
    }

    @PostMapping("/login")
    public List<CategoryEntity> login(@RequestBody Optional<UserLogin> userLogin) {
        System.out.println("userLogin = " + userLogin);
        List<CategoryEntity> categoryEntities = new ArrayList<>();
        if (userService.login(userLogin)) {
            return categoryService.getCategory();
        } else {
            categoryEntities.add(new CategoryEntity(0, "register", 0L));
        }
        return categoryEntities;
    }

    @PostMapping("/register")
    public List<CategoryEntity> register(@RequestBody Optional<UserRegister> userRegister) {
        System.out.println("userRegister = " + userRegister);
        List<CategoryEntity> categoryEntities = new ArrayList<>();

        if (userService.register(userRegister)) {
            return categoryService.getCategory();

        } else {
            categoryEntities.add(new CategoryEntity(0, "wrong_register", 0L));
        }
        return categoryEntities;
    }

    @PostMapping("/productList")
    public List<ProductEntity> productList(@RequestBody Optional<StringDto> name) {
        System.out.println("@PostMapping(\"/productList\") name = " + name);
        if (name.isPresent()) {
            return productService.getProducts(name.orElseThrow().getName());
        }
        return null;
    }


    @PostMapping("/product")
    public ProductEntity product(@RequestBody Optional<StringDto> name) {
        System.out.println("@PostMapping(\"/product\") name = " + name);
        if (name.isPresent()) {
            return productService.getProduct(name.orElseThrow().getName());
        }
        return null;
    }


    @PostMapping("/addOrderToCart")
    public List<CategoryEntity> addOrderToCart(@RequestBody Optional<OrderToCardDto> order) {
        System.out.println("@PostMapping(\"/addOrderToCart\") " + order);
        if (order.isPresent()) {
            orderProductService.addOrderToCard(order);
            return categoryService.getCategory();
        }
        return null;
    }


    @PostMapping("/addOrderToBuyed")
    public List<CategoryEntity> addOrderToBuyed(@RequestBody Optional<StringDto> stringDto) {
        System.out.println("@PostMapping(\"/addOrderToBuyed\") " + stringDto);
        if (stringDto.isPresent()) {
            orderProductService.addOrderToBuyed(stringDto);
            return categoryService.getCategory();
        }
        return null;
    }


    @PostMapping("/getCarts")
    public List<OrderProductEntity> getCarts(@RequestBody Optional<StringDto> stringDto) {
        System.out.println("@PostMapping(\"/getCarts\") " + stringDto);
        if (stringDto.isPresent()) {
            return orderProductService.getCarts(stringDto);
        }
        return null;
    }

    @PostMapping("/deleteBasket")
    public List<OrderProductEntity> deleteBasket(@RequestBody Optional<StringDto> stringDto) {
        System.out.println("@PostMapping(\"/deleteBasket\") " + stringDto);
        if (stringDto.isPresent()) {
            stringDto.orElse(null).setId(orderProductService.deleteBasket(stringDto));
            return orderProductService.getCarts(stringDto);
        }
        return null;
    }

    @PostMapping("/plusBasket")
    public List<OrderProductEntity> plusBasket(@RequestBody Optional<StringDto> stringDto) {
        System.out.println("@PostMapping(\"/plusBasket\") " + stringDto);
        if (stringDto.isPresent()) {
            stringDto.orElse(null).setId(orderProductService.plusBasket(stringDto));
            return orderProductService.getCarts(stringDto);
        }
        return null;
    }

    @PostMapping("/minusBasket")
    public List<OrderProductEntity> minusBasket(@RequestBody Optional<StringDto> stringDto) {
        System.out.println("@PostMapping(\"/minusBasket\") " + stringDto);
        if (stringDto.isPresent()) {
            stringDto.orElse(null).setId(orderProductService.minusBasket(stringDto));
            return orderProductService.getCarts(stringDto);
        }
        return null;
    }
}
