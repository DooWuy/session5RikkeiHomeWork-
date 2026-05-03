package com.customer.customerservice.service;

import com.customer.customerservice.dto.CustomerRequestDTO;
import com.customer.customerservice.dto.CustomerResponseDTO;
import com.customer.customerservice.entity.Customer;
import com.customer.customerservice.exception.ResourceNotFoundException;
import com.customer.customerservice.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;


    public CustomerResponseDTO createCustomer(CustomerRequestDTO requestDTO) {

        Customer customer = new Customer();
        customer.setFullName(requestDTO.getFullName());
        customer.setEmail(requestDTO.getEmail());
        customer.setPassword(requestDTO.getPassword());


        Customer savedCustomer =  customerRepository.save(customer);
        return mapToResponseDTO(savedCustomer);

    }

    public CustomerResponseDTO getCustomerById(Long id) throws ResourceNotFoundException {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
        return mapToResponseDTO(customer);
    }

    public List<CustomerResponseDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public CustomerResponseDTO updateCustomer(Long id, CustomerRequestDTO requestDTO) throws ResourceNotFoundException {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));

        customer.setFullName(requestDTO.getFullName());
        customer.setEmail(requestDTO.getEmail());
        customer.setPassword(requestDTO.getPassword());

        Customer updatedCustomer = customerRepository.save(customer);
        return mapToResponseDTO(updatedCustomer);
    }

    public void deleteCustomer(Long id) throws ResourceNotFoundException {
        if (!customerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Customer not found with id: " + id);
        }
        customerRepository.deleteById(id);
    }

    private CustomerResponseDTO mapToResponseDTO(Customer customer) {
        return new CustomerResponseDTO(customer.getId(), customer.getFullName(), customer.getEmail());
    }








}
