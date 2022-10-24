package uz.pdp.olchauz.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.pdp.olchauz.model.dto.receive.ProductReceiveDto;
import uz.pdp.olchauz.model.entity.UserEntity;
import uz.pdp.olchauz.service.BasketService;
import uz.pdp.olchauz.service.CategoryService;
import uz.pdp.olchauz.service.ProductService;
import uz.pdp.olchauz.service.UserService;

import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final ProductService productService;
    private final BasketService basketService;
    private final CategoryService categoryService;

    public AdminController(UserService userService, ProductService productService, BasketService basketService, CategoryService categoryService) {
        this.userService = userService;
        this.productService = productService;
        this.basketService = basketService;
        this.categoryService = categoryService;
    }

    @GetMapping("/users/{pageSize}")
    public String getUsers(
            Model model,
            @AuthenticationPrincipal UserEntity userEntity,
            @PathVariable Optional<Integer> pageSize) {
        int page = pageSize.orElse(1);
        model.addAttribute("userRole", userEntity.getRoleEntityList().get(0).getAuthority());
        model.addAttribute("userList", userService.getUsers(page));
        model.addAttribute("page", page);
        model.addAttribute("isEmpty", userService.getUsers(page + 1).isEmpty());
        return "index";
    }

    @GetMapping("/products/{pageSize}")
    public String getProducts(
            Model model,
            @AuthenticationPrincipal UserEntity userEntity,
            @PathVariable Optional<Integer> pageSize
    ) {
        int page = pageSize.orElse(1);
        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("userRole", userEntity.getRoleEntityList().get(0).getAuthority());
        model.addAttribute("productList", productService.getProduct(page));
        model.addAttribute("page", page);
        model.addAttribute("isEmpty", productService.getProduct(page + 1).isEmpty());
        return "index";
    }

    @GetMapping("/baskets/{pageSize}")
    public String getBaskets(
            Model model,
            @AuthenticationPrincipal UserEntity userEntity,
            @PathVariable Optional<Integer> pageSize) {
        int page = pageSize.orElse(1);
        model.addAttribute("userRole", userEntity.getRoleEntityList().get(0).getAuthority());
        model.addAttribute("basketList", basketService.getBaskets(page));
        model.addAttribute("page", page);
        model.addAttribute("isEmpty", basketService.getBaskets(page + 1).isEmpty());
        return "index";
    }

    @GetMapping("/category/{pageSize}")
    public String getCategory(
            Model model,
            @AuthenticationPrincipal UserEntity userEntity,
            @PathVariable Optional<Integer> pageSize) {
        int page = pageSize.orElse(1);
        model.addAttribute("userRole", userEntity.getRoleEntityList().get(0).getAuthority());
        model.addAttribute("categoryList", categoryService.getCategory(page));
        System.out.println("categoryService.getCategory(page) = " + categoryService.getCategory(page));
        model.addAttribute("page", page);
        model.addAttribute("isEmpty", categoryService.getCategory(page + 1).isEmpty());
        return "index";
    }

    @PostMapping("/{pageSize}/categoryAdd")
    public String addCategory(
            @RequestParam Optional<String> name,
            @RequestParam Optional<Long> parentId,
            @PathVariable Optional<Integer> pageSize
    ) {
        int page = pageSize.orElse(1);
        categoryService.addCategory(name, parentId);
        return "redirect:/admin/category/" + page + "";
    }

    @PostMapping("/{pageSize}/productAdd")
    public String addProduct(
            @ModelAttribute ProductReceiveDto productReceiveDto,
            @PathVariable Optional<Integer> pageSize
    ) throws IOException {
        int page = pageSize.orElse(1);
        System.out.println("productService = " + productService);
        productService.addProduct(productReceiveDto);
        return "redirect:/admin/products/" + page + "";
    }

    @PostMapping("/{pageSize}/categoryEdit")
    public String editCategory(
            @RequestParam Optional<Long> id,
            @RequestParam Optional<String> name,
            @RequestParam Optional<Long> parentId,
            @PathVariable Optional<Integer> pageSize
    ) {
        int page = pageSize.orElse(1);
        categoryService.editCategory(id, name, parentId);
        return "redirect:/admin/category/" + page + "";
    }

    @PostMapping("/{pageSize}/productEdit")
    public String editProduct(
            @RequestParam Optional<Long> id,
            @RequestParam Optional<String> name,
            @RequestParam Optional<Double> price,
            @PathVariable Optional<Integer> pageSize
    ) {
        int page = pageSize.orElse(1);
        productService.editProduct(id, name, price);
        return "redirect:/admin/products/" + page + "";
    }

    @GetMapping("/{pageSize}/categoryDelete/{id}")
    public String deleteCategory(
            @PathVariable Optional<Long> id,
            @PathVariable Optional<Integer> pageSize
    ) {
        int page = pageSize.orElse(1);
        categoryService.deleteCategory(id);
        return "redirect:/admin/category/" + page + "";
    }

    @GetMapping("/{pageSize}/productDelete/{id}")
    public String deleteProduct(
            @PathVariable Optional<Long> id,
            @PathVariable Optional<Integer> pageSize
    ) {
        int page = pageSize.orElse(1);
        productService.deleteProduct(id);
        return "redirect:/admin/products/" + page + "";
    }

    @GetMapping("/{pageSize}/delivered/{id}")
    public String delivered(
            @PathVariable Optional<Long> id,
            @PathVariable Optional<Integer> pageSize
    ) {
        int page = pageSize.orElse(1);
        basketService.delivered(id);
        return "redirect:/admin/baskets/" + page + "";
    }

}
