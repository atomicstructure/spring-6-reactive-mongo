package com.samantha.spring6reactivemongo.web.fn;


import com.samantha.spring6reactivemongo.model.BeerDTO;
import com.samantha.spring6reactivemongo.services.BeerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class BeerHandler {
    private final BeerService beerService;

    public Mono<ServerResponse> listBeers(ServerResponse serverResponse) {
        return ServerResponse.ok()
                .body(beerService.listBeers(), BeerDTO.class);
    }

}
