package uz.pdp.olchauz.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.pdp.olchauz.model.entity.RoleEntity;
import uz.pdp.olchauz.model.repository.UserRepository;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class JwtTokenFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;

    @Value("${jwt.expiration.date}")
    private long expirationDate;

    @Value("${jwt.secret.key}")
    private String secretKey;

    public JwtTokenFilter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authToken = request.getHeader("authToken");

        if ((authToken == null || !authToken.startsWith("Bearer "))) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = authToken.replace("Bearer ", "");
        final Claims claims = getClaims(token);
        setAuthentication(claims);
        filterChain.doFilter(request, response);
    }

    private void setAuthentication(final Claims claims) throws JsonProcessingException {
        System.out.println("claims = " + claims);
        Object roles = claims.get("roles");
        String s = new ObjectMapper().writeValueAsString(roles);
        System.out.println(s);
        List<RoleEntity> simpleGrantedAuthorities = new ObjectMapper().readValue(s, new TypeReference<List<RoleEntity>>() {
        });
        Authentication authentication = new UsernamePasswordAuthenticationToken(claims.getSubject(), null, simpleGrantedAuthorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

//    private UserEntity getUser(final String token) {
//        if (isValidToken(token)) {
//            final Claims claims = getClaims(token);
//            if (optionalUserEntity.isEmpty()) {
//                throw new UserNotFoundException(username + " not  found");
//            }
//            return optionalUserEntity.get();
//        } else {
//            return null;
//        }
//    }

    private Claims getClaims(final String token) {
        try {
            return Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new JwtException("Jwt parse exception");
        }
    }

    private boolean isValidToken(final String token) {
        final Claims claims = getClaims(token);
        final Date expiryDate = claims.getExpiration();
        if (expiryDate.getTime() < System.currentTimeMillis()) {
            throw new JwtException("token is expired");
        }
        return true;
    }

}
