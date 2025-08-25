package com.samantha.spring6reactivemongo.mappers;

import com.samantha.spring6reactivemongo.domain.Customer;
import com.samantha.spring6reactivemongo.model.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {

    CustomerDTO customerToCustomerDto(Customer customer);

    Customer customerDtoToCustomer(CustomerDTO customerDTO);
}
