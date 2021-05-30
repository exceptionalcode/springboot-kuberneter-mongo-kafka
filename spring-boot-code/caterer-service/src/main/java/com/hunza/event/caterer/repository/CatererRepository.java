package com.hunza.event.caterer.repository;

import com.hunza.event.caterer.model.Caterer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author ishaan.solanki
 * <p>
 * It is a class responsible to interact with database of caterer service.
 * <p>This is a class to perform database operation read, write and update on hunza database.</p>
 */
@Repository
public interface CatererRepository extends MongoRepository<Caterer, Long> {
    /**
     * This abstract method will find caterer by location.cityname
     *
     * @param cityName {@link String}
     * @param pageable {@link Pageable}
     * @return {@link Page}
     */
    Page<Caterer> findCatererByLocation_CityName(String cityName, Pageable pageable);

    /**
     * This abstract method will find caterer by name
     *
     * @param name{@link String}
     * @return {@link Optional}
     */
    Optional<Caterer> findByName(String name);
}
