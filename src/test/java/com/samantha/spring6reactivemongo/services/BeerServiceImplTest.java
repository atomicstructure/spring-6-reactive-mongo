package com.samantha.spring6reactivemongo.services;

import com.mongodb.annotations.ThreadSafe;
import com.samantha.spring6reactivemongo.domain.Beer;
import com.samantha.spring6reactivemongo.mappers.BeerMapper;
import com.samantha.spring6reactivemongo.model.BeerDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class BeerServiceImplTest {

    @Autowired
    BeerService beerService;

    @Autowired
    BeerMapper beerMapper;

    BeerDTO beerDTO;

    @BeforeEach
    void  setUp(){
        beerDTO = beerMapper.beerToBeerDto(getTestBeer());
    }
    @Test
    void saveBeer(){
        Mono<BeerDTO> savedMono = beerService.saveBeer(Mono.just(beerDTO));

        savedMono.subscribe(savedDto -> {
            System.out.println(savedDto.getId());
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static Beer getTestBeer() {
        return Beer.builder()
                .beerName("Space Dust")
                .beerStyle("IPA")
                .price(BigDecimal.TEN)
                .quantityOnHand(12)
                .upc("123213")
                .build();
    }

}