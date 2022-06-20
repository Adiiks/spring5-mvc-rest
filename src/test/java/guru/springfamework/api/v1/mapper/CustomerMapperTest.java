package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerMapperTest {

    CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    public static final String FIRSTNAME = "Marian";
    public static final String LASTNAME = "Malinowski";

    @Test
    public void customerToCustomerDTO() {

        Customer customer = new Customer();
        customer.setFirstname(FIRSTNAME);
        customer.setLastname(LASTNAME);

        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);

        assertEquals(FIRSTNAME, customerDTO.getFirstname());
        assertEquals(LASTNAME, customerDTO.getLastname());
    }

    @Test
    public void customerDTOToCustomer() {

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(FIRSTNAME);
        customerDTO.setLastname(LASTNAME);

        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);

        assertEquals(FIRSTNAME, customer.getFirstname());
        assertEquals(LASTNAME, customer.getLastname());
    }
}