package com.samantha.spring6reactivemongo.services;

import com.samantha.spring6reactivemongo.model.BeerDTO;
import com.samantha.spring6reactivemongo.model.CustomerDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {
    Flux<CustomerDTO> listCustomer();

    Mono<CustomerDTO> saveNewCustomer(Mono<CustomerDTO> customerDTOMono);

    Mono<CustomerDTO> saveNewCustomer(CustomerDTO customerDTO);

    Mono<CustomerDTO> getById(String customerId);

    Mono<CustomerDTO> updateName(String customerId, CustomerDTO customerDTO);

    Mono<CustomerDTO> patchName(String customerId, CustomerDTO customerDTO);

    Mono<Void> deleteCustomerById(String customerId);
}
