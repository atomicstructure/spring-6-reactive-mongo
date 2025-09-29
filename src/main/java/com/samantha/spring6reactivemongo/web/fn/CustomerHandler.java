package com.samantha.spring6reactivemongo.web.fn;

import com.samantha.spring6reactivemongo.model.CustomerDTO;
import com.samantha.spring6reactivemongo.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebInputException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CustomerHandler {
    private final CustomerService customerService;
    private final Validator validator;

    private void validate(CustomerDTO customerDTO){
        Errors errors = new BeanPropertyBindingResult(customerDTO, "customerDto");
        validator.validate(customerDTO, errors);

        if (errors.hasErrors()){
            throw new ServerWebInputException(errors.toString());
        }
    }

    public Mono<ServerResponse> deleteById(ServerRequest serverRequest) {
        return customerService.getById(serverRequest.pathVariable("customerId"))
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .flatMap(customerDTO -> customerService.deleteCustomerById(customerDTO.getId()))
                .then(ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> PatchById(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(CustomerDTO.class)
                .doOnNext(this::validate)
                .flatMap(customerDTO -> customerService
                        .patchName(serverRequest.pathVariable("customerId"), customerDTO))
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .flatMap(savedDto -> ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> UpdateNameById(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(CustomerDTO.class)
                .doOnNext(this::validate)
                .flatMap(customerDTO -> customerService
                        .updateName(serverRequest.pathVariable("customerId"), customerDTO))
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .flatMap(savedDto -> ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> createCustomerName(ServerRequest serverRequest) {
        return customerService.saveNewCustomer(serverRequest.bodyToMono(CustomerDTO.class).doOnNext(this::validate))
                .flatMap(customerDTO -> ServerResponse
                        .created(UriComponentsBuilder
                                .fromPath(CustomerRouterConfig.CUSTOMER_PATH_ID)
                                .build(customerDTO.getId()))
                        .build());
    }
    public Mono<ServerResponse> getCustomerById(ServerRequest serverRequest) {
        return ServerResponse
                .ok()
                .body(customerService.getById(serverRequest.pathVariable("customerId"))
                                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                        , CustomerDTO.class)
                ;
    }
    public Mono<ServerResponse> listCustomers(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .body(customerService.listCustomer(), CustomerDTO.class)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

}