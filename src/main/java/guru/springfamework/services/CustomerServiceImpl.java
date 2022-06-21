package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.model.CustomerListDTO;
import guru.springfamework.controllers.v1.CustomerController;
import guru.springfamework.domain.Customer;
import guru.springfamework.exceptions.ResourceNotFoundException;
import guru.springfamework.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper = CustomerMapper.INSTANCE;

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
            customerDTO.setCustomerUrl(CustomerController.BASE_URL + "/" + customer.getId());
            customerDTOS.add(customerDTO);
        });

        customerListDTO.setCustomers(customerDTOS);
        return customerListDTO;
    }

    @Override
    public CustomerDTO findCustomerById(Long id) {
        return customerMapper.customerToCustomerDTO(customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found. For ID: " + id)));
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
        return saveAndReturnDTO(customerMapper.customerDTOToCustomer(customerDTO));
    }

    @Override
    public CustomerDTO saveOrUpdateCustomer(Long id, CustomerDTO customerDTO) {

        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
        customer.setId(id);

        return saveAndReturnDTO(customer);
    }

    private CustomerDTO saveAndReturnDTO(Customer customer) {

        Customer savedCustomer = customerRepository.save(customer);

        CustomerDTO newCustomerDTO = customerMapper.customerToCustomerDTO(savedCustomer);

        newCustomerDTO.setCustomerUrl(CustomerController.BASE_URL + "/" + savedCustomer.getId());

        return newCustomerDTO;
    }

    @Override
    public CustomerDTO patchCustomer(CustomerDTO customerDTO, Long id) {
        return customerRepository.findById(id)
                .map(customer -> {

                    if (customerDTO.getFirstname() != null) {
                        customer.setFirstname(customerDTO.getFirstname());
                    }

                    if (customerDTO.getLastname() != null) {
                        customer.setLastname(customerDTO.getLastname());
                    }

                    CustomerDTO savedCustomerDTO =
                            customerMapper.customerToCustomerDTO(customerRepository.save(customer));
                    savedCustomerDTO.setCustomerUrl(CustomerController.BASE_URL + "/" + id);

                    return savedCustomerDTO;
                }).orElseThrow(() -> new ResourceNotFoundException("Customer not found. For ID: " + id));
    }

    @Override
    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }
}
