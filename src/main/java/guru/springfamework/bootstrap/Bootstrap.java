package guru.springfamework.bootstrap;

import guru.springfamework.domain.Category;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class Bootstrap implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final CustomerRepository customerRepository;

    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        loadCategories();
        loadCustomers();
    }

    private void loadCategories() {

        Category fruits = new Category();
        fruits.setName("Fruits");

        Category dried = new Category();
        dried.setName("Dried");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");

        List<Category> categories = new ArrayList<>();
        categories.add(fruits);
        categories.add(dried);
        categories.add(fresh);
        categories.add(exotic);
        categories.add(nuts);

        categoryRepository.saveAll(categories);

        System.out.println("Categories loaded = " + categoryRepository.count());
    }

    private void loadCustomers() {

        Customer adrian = new Customer();
        adrian.setFirstname("Adrian");
        adrian.setLastname("Najlepszy");

        Customer marek = new Customer();
        marek.setFirstname("Marek");
        marek.setLastname("Jechała");

        Customer darek = new Customer();
        darek.setFirstname("Darek");
        darek.setLastname("Krzynówek");

        List<Customer> customers = Arrays.asList(adrian, marek, darek);

        customerRepository.saveAll(customers);

        System.out.println("Customers loaded = " + customerRepository.count());
    }
}
