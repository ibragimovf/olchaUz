package uz.pdp.olchauz.model.dto.receive;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductReceiveDto {
    private String name;
    private Double price;
    private MultipartFile image;
    private Long categoryId;
}
