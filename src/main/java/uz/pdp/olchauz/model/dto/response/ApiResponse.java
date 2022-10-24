package uz.pdp.olchauz.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiResponse {

    @JsonProperty("status_code")
    private int statusCode;
    private String message;

    private Map<Object, Object> data;


}
