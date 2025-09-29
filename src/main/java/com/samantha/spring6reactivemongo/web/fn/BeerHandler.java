package com.samantha.spring6reactivemongo.web.fn;


import com.mongodb.internal.connection.Server;
import com.samantha.spring6reactivemongo.model.BeerDTO;
import com.samantha.spring6reactivemongo.services.BeerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.awt.event.MouseMotionAdapter;
import java.net.URI;

@Component
@RequiredArgsConstructor
public class BeerHandler {
    private final BeerService beerService;

    public Mono<ServerResponse> PatchById(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(BeerDTO.class)
                .flatMap(beerDTO -> beerService
                        .patchBeer(serverRequest.pathVariable("beerId"), beerDTO))
                .flatMap(savedDto -> ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> UpdateBeerById(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(BeerDTO.class)
                .flatMap(beerDTO -> beerService
                        .updateBeer(serverRequest.pathVariable("beerId"), beerDTO))
                .flatMap(savedDto -> ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> createBeer(ServerRequest serverRequest) {
        return beerService.saveBeer(serverRequest.bodyToMono(BeerDTO.class))
                .flatMap(beerDTO -> ServerResponse
                        .created(UriComponentsBuilder
                                .fromPath(BeerRouterConfig.BEER_PATH_ID)
                                .build(beerDTO.getId()))
                        .build());
    }
    public Mono<ServerResponse> getBeerById(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .body(beerService.getById(serverRequest.pathVariable("beerId")), BeerDTO.class);
    }
    public Mono<ServerResponse> listBeers(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .body(beerService.listBeers(), BeerDTO.class);
    }

}
