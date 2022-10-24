package uz.pdp.olchauz.model.dto.receive;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderToCardDto {
    private Long id;
    private Long customerId;
    private String productId;
    private String username;
    private Integer count;
}
