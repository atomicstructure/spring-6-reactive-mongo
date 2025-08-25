package com.samantha.spring6reactivemongo.mappers;

import com.samantha.spring6reactivemongo.domain.Beer;
import com.samantha.spring6reactivemongo.model.BeerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {

    BeerDTO beerToBeerDto(Beer beer);

    Beer beerDtoToBeer(BeerDTO beerDTO);
}
