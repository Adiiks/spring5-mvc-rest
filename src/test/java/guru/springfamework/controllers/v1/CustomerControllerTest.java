package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.model.CustomerListDTO;
import guru.springfamework.controllers.RestResponseEntityExceptionHandler;
import guru.springfamework.exceptions.ResourceNotFoundException;
import guru.springfamework.services.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static guru.springfamework.controllers.v1.AbstractRestController.asJsonString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerControllerTest {

    public static final String FIRSTNAME = "Adrian";
    public static final String LASTNAME = "Paździoch";
    public static final String CUSTOMER_URL = "/api/v1/customers/1";

    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    MockMvc mockMvc;
    CustomerDTO customerDTO;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(customerController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler()).build();

        createCustomer();
    }

    @Test
    public void getAllCustomers() throws Exception {

        CustomerDTO customerDTO2 = new CustomerDTO();
        customerDTO2.setFirstname("Jacek");
        customerDTO2.setLastname("Długi");
        customerDTO2.setCustomerUrl("/customers/2");

        CustomerListDTO customerListDTO = new CustomerListDTO();
        customerListDTO.setCustomers(Arrays.asList(customerDTO, customerDTO2));

        when(customerService.getAllCustomers()).thenReturn(customerListDTO);

        mockMvc.perform(get(CustomerController.BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));
    }

    @Test
    public void getCustomerById() throws Exception {

        when(customerService.findCustomerById(anyLong())).thenReturn(customerDTO);

        mockMvc.perform(get(CustomerController.BASE_URL + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo(FIRSTNAME)))
                .andExpect(jsonPath("$.lastname", equalTo(LASTNAME)));
    }

    @Test
    public void getCustomerByIdNotFound() throws Exception {

        when(customerService.findCustomerById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(CustomerController.BASE_URL + "/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createNewCustomer() throws Exception {

        CustomerDTO customerDTOToSave = new CustomerDTO();
        customerDTOToSave.setFirstname(FIRSTNAME);
        customerDTOToSave.setLastname(LASTNAME);

        when(customerService.createNewCustomer(any(CustomerDTO.class))).thenReturn(customerDTO);

        mockMvc.perform(post(CustomerController.BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(customerDTOToSave)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstname", equalTo(FIRSTNAME)))
                .andExpect(jsonPath("$.lastname", equalTo(LASTNAME)))
                .andExpect(jsonPath("$.customerUrl", equalTo(CUSTOMER_URL)));
    }

    @Test
    public void updateCustomer() throws Exception {

        CustomerDTO customerDTOToSave = new CustomerDTO();
        customerDTOToSave.setFirstname(FIRSTNAME);
        customerDTOToSave.setLastname(LASTNAME);

        when(customerService.saveOrUpdateCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(customerDTO);

        mockMvc.perform(put(CustomerController.BASE_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(customerDTOToSave)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo(FIRSTNAME)))
                .andExpect(jsonPath("$.lastname", equalTo(LASTNAME)))
                .andExpect(jsonPath("$.customerUrl", equalTo(CUSTOMER_URL)));
    }

    @Test
    public void patchCustomer() throws Exception {

        String updatedFirstname = "Michał";

        CustomerDTO customerDTOToUpdate = new CustomerDTO();
        customerDTOToUpdate.setFirstname(updatedFirstname);

        customerDTO.setFirstname(updatedFirstname);

        when(customerService.patchCustomer(any(CustomerDTO.class), anyLong())).thenReturn(customerDTO);

        mockMvc.perform(patch(CustomerController.BASE_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(customerDTOToUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo(updatedFirstname)))
                .andExpect(jsonPath("$.lastname", equalTo(LASTNAME)))
                .andExpect(jsonPath("$.customerUrl", equalTo(CUSTOMER_URL)));
    }

    @Test
    public void patchCustomerNotFound() throws Exception {

        String updatedFirstname = "Michał";

        CustomerDTO customerDTOToUpdate = new CustomerDTO();
        customerDTOToUpdate.setFirstname(updatedFirstname);

        when(customerService.patchCustomer(any(CustomerDTO.class), anyLong()))
                .thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(patch(CustomerController.BASE_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(customerDTOToUpdate)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteCustomerById() throws Exception {

        mockMvc.perform(delete(CustomerController.BASE_URL + "/1"))
                .andExpect(status().isOk());

        verify(customerService, times(1)).deleteCustomerById(anyLong());
    }

    private void createCustomer() {
        customerDTO = new CustomerDTO();
        customerDTO.setFirstname(FIRSTNAME);
        customerDTO.setLastname(LASTNAME);
        customerDTO.setCustomerUrl(CUSTOMER_URL);
    }
}