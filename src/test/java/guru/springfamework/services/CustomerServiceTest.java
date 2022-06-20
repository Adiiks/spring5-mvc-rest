package guru.springfamework.services;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.model.CustomerListDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class CustomerServiceTest {

    public static final Long ID = 1L;
    public static final String FIRSTNAME = "Adrian";
    public static final String LASTNAME = "Wajcha";
    public static final String CUSTOMER_URL = "/api/v1/customers/";

    CustomerService customerService;

    @Mock
    CustomerRepository customerRepository;

    Customer customer;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        customerService = new CustomerServiceImpl(customerRepository);

        createCustomer();
    }

    @Test
    public void getAllCustomers() {

        Customer customer2 = new Customer();
        customer2.setId(ID + 1);
        customer2.setFirstname("Marian");
        customer2.setLastname("Twaróg");

        List<Customer> customers = Arrays.asList(customer, customer2);

        when(customerRepository.findAll()).thenReturn(customers);

        CustomerListDTO customerListDTO = customerService.getAllCustomers();

        assertEquals(2, customerListDTO.getCustomers().size());
    }

    @Test
    public void findCustomerById() {

        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));

        CustomerDTO customerDTO = customerService.findCustomerById(ID);

        assertEquals(FIRSTNAME, customerDTO.getFirstname());
        assertEquals(LASTNAME, customerDTO.getLastname());
        assertNull(customerDTO.getCustomerUrl());
    }

    @Test
    public void createNewCustomer() {

        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        CustomerDTO customerDTO = customerService.createNewCustomer(createCustomerDTO());

        assertEquals(FIRSTNAME, customerDTO.getFirstname());
        assertEquals(LASTNAME, customerDTO.getLastname());
        assertEquals(CUSTOMER_URL + ID, customerDTO.getCustomerUrl());
    }

    private void createCustomer() {

        customer = new Customer();
        customer.setId(ID);
        customer.setFirstname(FIRSTNAME);
        customer.setLastname(LASTNAME);
    }

    private CustomerDTO createCustomerDTO() {

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(FIRSTNAME);
        customerDTO.setLastname(LASTNAME);

        return customerDTO;
    }
}