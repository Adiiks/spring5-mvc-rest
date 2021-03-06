package guru.springfamework.services;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.model.CustomerListDTO;

public interface CustomerService {

    CustomerListDTO getAllCustomers();

    CustomerDTO findCustomerById(Long id);

    CustomerDTO createNewCustomer(CustomerDTO customerDTO);

    CustomerDTO saveOrUpdateCustomer(Long id, CustomerDTO customerDTO);

    CustomerDTO patchCustomer(CustomerDTO customerDTO, Long id);

    void deleteCustomerById(Long id);
}
