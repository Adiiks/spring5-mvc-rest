package guru.springfamework.api.v1.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;

@Data
public class CustomerDTO {

    private String firstname;
    private String lastname;

    @JsonInclude(Include.NON_NULL)
    private String customerUrl;
}
