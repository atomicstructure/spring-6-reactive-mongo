package com.samantha.spring6reactivemongo.services;

import com.samantha.spring6reactivemongo.domain.Beer;
import com.samantha.spring6reactivemongo.model.BeerDTO;
import reactor.core.publisher.Mono;

public interface BeerService {

    Mono<BeerDTO> saveBeer(BeerDTO beerDTO);

    Mono<BeerDTO> getById(String beerId);
}
