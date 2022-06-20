package guru.springfamework.api.v1.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CustomerListDTO {

    private List<CustomerDTO> customers = new ArrayList<>();
}
