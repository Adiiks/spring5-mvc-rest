package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.model.CustomerListDTO;
import guru.springfamework.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<CustomerListDTO> getAllCustomers() {
        return ResponseEntity.ok().body(customerService.getAllCustomers());
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long customerId) {
        return ResponseEntity.ok().body(customerService.findCustomerById(customerId));
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> createNewCustomer(@RequestBody CustomerDTO customerDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.createNewCustomer(customerDTO));
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> updateCustomer(@RequestBody CustomerDTO customerDTO,
                                                      @PathVariable Long customerId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(customerService.saveOrUpdateCustomer(customerId, customerDTO));
    }

    @PatchMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> patchCustomer(@RequestBody CustomerDTO customerDTO,
                                                      @PathVariable Long customerId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(customerService.patchCustomer(customerDTO, customerId));
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity deleteCustomerById(@PathVariable Long customerId) {
        customerService.deleteCustomerById(customerId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
