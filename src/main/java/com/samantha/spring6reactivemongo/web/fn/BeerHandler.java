package com.samantha.spring6reactivemongo.web.fn;


import com.mongodb.internal.connection.Server;
import com.samantha.spring6reactivemongo.model.BeerDTO;
import com.samantha.spring6reactivemongo.services.BeerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class BeerHandler {
    private final BeerService beerService;

    public Mono<ServerResponse> getBeerById(ServerRequest serverRequest) {
        return ServerResponse.ok().body(beerService.getById(serverRequest.pathVariable("beerId")), BeerDTO.class);
    }
    public Mono<ServerResponse> listBeers(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .body(beerService.listBeers(), BeerDTO.class);
    }

}
