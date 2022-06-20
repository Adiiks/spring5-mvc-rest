package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.model.CustomerListDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    private final String CUSTOMER_URL = "/api/v1/customers/";

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerListDTO getAllCustomers() {

        CustomerListDTO customerListDTO = new CustomerListDTO();

        List<Customer> customers = customerRepository.findAll();
        List<CustomerDTO> customerDTOS = new ArrayList<>();

        customers.forEach(customer -> {
            CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
            customerDTO.setCustomerUrl(CUSTOMER_URL + customer.getId());
            customerDTOS.add(customerDTO);
        });

        customerListDTO.setCustomers(customerDTOS);
        return customerListDTO;
    }

    @Override
    public CustomerDTO findCustomerById(Long id) {
        return customerMapper.customerToCustomerDTO(customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found. For ID: " + id)));
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {

        Customer customerToSave = customerMapper.customerDTOToCustomer(customerDTO);

        Customer savedCustomer = customerRepository.save(customerToSave);

        CustomerDTO newCustomerDTO = customerMapper.customerToCustomerDTO(savedCustomer);

        newCustomerDTO.setCustomerUrl(CUSTOMER_URL + savedCustomer.getId());

        return newCustomerDTO;
    }
}
