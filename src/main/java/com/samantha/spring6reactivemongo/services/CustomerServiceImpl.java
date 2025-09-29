package com.samantha.spring6reactivemongo.services;

import com.samantha.spring6reactivemongo.mappers.CustomerMapper;
import com.samantha.spring6reactivemongo.model.CustomerDTO;
import com.samantha.spring6reactivemongo.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;
    @Override
    public Flux<CustomerDTO> listCustomer() {
        return customerRepository.findAll()
                .map(customerMapper::customerToCustomerDto);
    }


    @Override
    public Mono<CustomerDTO> saveNewCustomer(Mono<CustomerDTO> customerDTOMono) {
        return customerDTOMono.map(customerMapper::customerDtoToCustomer)
                .flatMap(customerRepository::save)
                .map(customerMapper::customerToCustomerDto);
    }

    @Override
    public Mono<CustomerDTO> saveNewCustomer(CustomerDTO customerDTO) {
        return customerRepository.save(customerMapper.customerDtoToCustomer(customerDTO))
                .map(customerMapper::customerToCustomerDto);
    }

    @Override
    public Mono<CustomerDTO> getById(String customerId) {
        return customerRepository.findById(customerId)
                .map(customerMapper::customerToCustomerDto);
    }

    @Override
    public Mono<CustomerDTO> updateName(String customerId, CustomerDTO customerDTO) {
        return customerRepository.findById(customerId)
                .map(foundCustomer -> {;
                    //update properties
                    foundCustomer.setCustomerName(customerDTO.getCustomerName());
                    return foundCustomer;
                })
                .flatMap(customerRepository::save)
                .map(customerMapper::customerToCustomerDto);
    }

    @Override
    public Mono<CustomerDTO> patchName(String customerId, CustomerDTO customerDTO) {
        return customerRepository.findById(customerId)
                .map(foundCustomer -> {;
                    //update properties
                    if (customerDTO.getCustomerName() != null) {
                        foundCustomer.setCustomerName(customerDTO.getCustomerName());
                    }
                    return foundCustomer;
                })
                .flatMap(customerRepository::save)
                .map(customerMapper::customerToCustomerDto);
    }

    @Override
    public Mono<Void> deleteCustomerById(String customerId) {
        return customerRepository.deleteById(customerId);
    }
}
