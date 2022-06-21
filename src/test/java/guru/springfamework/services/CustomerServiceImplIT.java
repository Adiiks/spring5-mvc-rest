package guru.springfamework.services;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.bootstrap.Bootstrap;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerServiceImplIT {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    CustomerService customerService;

    @Before
    public void setUp() throws Exception {

        System.out.println("Loading customer data");
        System.out.println(customerRepository.count());

        Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository);
        bootstrap.run();

        customerService = new CustomerServiceImpl(customerRepository);
    }

    @Test
    public void patchCustomerUpdateFirstname() {

        String firstname = "Janek";

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(firstname);

        Customer originalCustomer = customerRepository.findById(getCustomerId()).get();
        String originalFirstname = originalCustomer.getFirstname();
        String originalLastname = originalCustomer.getLastname();

        CustomerDTO updatedCustomer = customerService.patchCustomer(customerDTO, getCustomerId());

        assertEquals(firstname, updatedCustomer.getFirstname());
        assertNotEquals(originalFirstname, updatedCustomer.getFirstname());
        assertEquals(originalLastname, updatedCustomer.getLastname());
    }

    @Test
    public void patchCustomerUpdateLastname() {

        String lastname = "Boniek";

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setLastname(lastname);

        Customer originalCustomer = customerRepository.findById(getCustomerId()).get();
        String originalFirstname = originalCustomer.getFirstname();
        String originalLastname = originalCustomer.getLastname();

        CustomerDTO updatedCustomer = customerService.patchCustomer(customerDTO, getCustomerId());

        assertEquals(lastname, updatedCustomer.getLastname());
        assertEquals(originalFirstname, updatedCustomer.getFirstname());
        assertNotEquals(originalLastname, updatedCustomer.getLastname());
    }

    private Long getCustomerId() {
        return customerRepository.findAll().get(0).getId();
    }
}
