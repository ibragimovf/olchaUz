package uz.pdp.olchauz.model.dto.receive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRegister {
    private String fullName;
    private String username;
    private String password;
}
