package uz.pdp.olchauz.filter;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.olchauz.auth.AuthService;
import uz.pdp.olchauz.model.dto.receive.UserLogin;
import uz.pdp.olchauz.model.dto.response.ApiResponse;

import java.util.Date;
import java.util.HashMap;

@Service
public class AuthenticationInitFilter {

    @Value("${jwt.expiration.date}")
    private long expirationDate;

    @Value("${jwt.secret.key}")
    private String secretKey;


    private final AuthService authService;

    public AuthenticationInitFilter(AuthService authService) {
        this.authService = authService;
    }

    public ApiResponse attemptAuthentication(final UserLogin userLogin) {
        try {

            UserDetails userDetails = authService.loadUserByUsername(userLogin.getUsername());
            if (userDetails == null) {
                throw new UsernameNotFoundException("user not found");
            } else {
                if (!new BCryptPasswordEncoder().matches(userLogin.getPassword(), userDetails.getPassword())) {
                    throw new UsernameNotFoundException("user password is incorrect");
                } else {
                    String jwtToken = createJwt(userDetails);
                    HashMap<Object, Object> map = new HashMap<>();
                    map.put("access_token", jwtToken);

                    ApiResponse apiResponse = new ApiResponse(
                            0,
                            "success",
                            map
                    );
                    return apiResponse;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private String createJwt(final UserDetails userDetails) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, "blablablabla")
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 86400000))
                .setSubject(userDetails.getUsername())
                .claim("roles", userDetails.getAuthorities())
                .compact();
    }
}
