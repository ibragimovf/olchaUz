package uz.pdp.olchauz.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.pdp.olchauz.model.dto.receive.PermissionDto;
import uz.pdp.olchauz.service.UserService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/superAdmin")
public class SuperAdminController {
    private final UserService userService;

    public SuperAdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/add")
    public String getSuperAdmin() {
        userService.addSuperAdmin();
        return "Created super admin";
    }

    @GetMapping("/permission")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public String getPermission() {
        return "permission";
    }

    @PostMapping("/permission/add")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public String addPermission(
            @RequestBody PermissionDto permissionDto,
            Model model
    ) throws JsonProcessingException {
        boolean isSuccess = userService.addPermission(permissionDto);
        model.addAttribute("isSuccess", isSuccess);
        return "permission";
    }

    @RequestMapping("/default")
    public String defaultAfterLogin(HttpServletRequest request) {
        if (request.isUserInRole("ROLE_SUPER_ADMIN")) {
            return "redirect:/superAdmin/permission";
        }
        return "redirect:/admin/users/1";
    }
}
