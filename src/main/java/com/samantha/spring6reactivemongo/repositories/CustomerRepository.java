package com.samantha.spring6reactivemongo.repositories;

import com.samantha.spring6reactivemongo.domain.Customer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import java.util.Optional;

public interface CustomerRepository extends ReactiveMongoRepository<Customer, String> {
    Optional<Object> findFirstByCustomerName(String customerName);
}
