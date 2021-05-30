package com.hunza.event.caterer.service;

import com.hunza.event.caterer.model.Caterer;
import com.hunza.event.caterer.model.DbSequence;
import com.hunza.event.caterer.repository.CatererRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import static com.hunza.event.caterer.model.Caterer.SEQUENCE_NAME;
import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;

/**
 * @author ishaan.solanki
 * <p>
 * It is class{@link CatererService} responsible for business logic for caterer application system.
 * <p>This service layer will iteract with db layer</p>
 */
@Service
public class CatererService {

    /**
     * logger for {@link CatererService}
     */
    private static final Logger logger = LoggerFactory.getLogger(CatererService.class);

    @Autowired
    private CatererRepository catererRepository;

    @Autowired
    private MongoOperations mongoOperations;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    /**
     * Placeholder for page size pulled from kubernetes configuration ,It's default values is 10.
     */
    @Value("${caterer.pagesize}")
    private Integer pageSize;

    /**
     * Method responsible for adding or updating a caterer.
     * <p>It will takes input of {@link Caterer} object and calls repository to save and update</p>
     *
     * @param caterer {@link Caterer}
     * @return {@link Caterer}
     */
    public Caterer addCaterer(Caterer caterer) {
        logger.info("Add caterer in DB with object with payload {}", caterer);
        Caterer savedCaterer;

        //Checks first if caterer by name already exist in DB.
        Optional<Caterer> catererOptional = catererRepository.findByName(caterer.getName());

        //If not present then save to DB and new Caterer.
        if (!catererOptional.isPresent()) {

            //Auto generate an ID sequence by the below method
            long catererId = getSequenceNumber(SEQUENCE_NAME);
            caterer.setId(catererId);
            savedCaterer = catererRepository.save(caterer);

            if (null != savedCaterer) {
                //If new caterer successfully saved produce a message with caterer name to kafka broker.
                kafkaProducerService.produce(caterer.getName());
            }
            return savedCaterer;
        }

        //If caterer already exist pull out the caterer id to update the existing entry in db.
        caterer.setId(catererOptional.get().getId());
        savedCaterer = catererRepository.save(caterer);

        //Produces the message with caterer name on kafka
        kafkaProducerService.produce(caterer.getName());
        return savedCaterer;
    }

    /**
     * Method responsible for fetch all the caterers and cache it.
     * <p>It will pull the List of caterer present in the db</p>
     *
     * @return {@link Collection}
     */
    @Cacheable(cacheNames = "caterer")
    public Collection<Caterer> getAllCaterers() {
        logger.info("Get all caterers");
        return catererRepository.findAll();
    }

    /**
     * Method responsible for fetch the caterer by Id and cache it.
     * <p>It will pull the caterer present in the db by it's Id</p>
     *
     * @param id {@link Long}
     * @return {@link Optional}
     */
    @Cacheable(cacheNames = "caterer", key = "#id")
    public Optional<Caterer> getCatererById(Long id) {
        logger.info("Get caterer by id {}", id);
        return catererRepository.findById(id);
    }

    /**
     * Method responsible for fetch the caterer by Name and cache it.
     * <p>It will pull the caterer present in the db by it's Name</p>
     *
     * @param name {@link String}
     * @return {@link Optional}
     */
    @Cacheable(cacheNames = "caterer", key = "#name")
    public Optional<Caterer> getCatererByName(String name) {
        logger.info("Get caterer by name {}", name);
        return catererRepository.findByName(name);
    }

    /**
     * Method responsible for fetch the caterer by City Name as per the Page wise request.
     * <p>It will pull the caterer present in the db by it's City Name</p>
     *
     * @param cityName {@link String}
     * @param page     {@link Integer}
     * @return {@link Page}
     */
    public Page<Caterer> getCatererByCityName(String cityName, Integer page) {
        logger.info("Get caterer by cityName {} and page {}", cityName, page);
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
        return catererRepository.findCatererByLocation_CityName(cityName, pageRequest);
    }

    /**
     * Method is responsible to evict all the cache entries present at once.
     * <p>It will delete the cache entries present in cache storage</p>
     */
    @CacheEvict(value = "caterer", allEntries = true)
    public void clearCache() {
        // intentionally left blank
    }


    /**
     * Method is responsible to auto increment the sequence by 1 for every new Caterer entry.
     * <p>It will increment the last present id in db by 1.</p>
     *
     * @param sequenceName {@link String}
     * @return {@link Long}
     */
    private long getSequenceNumber(String sequenceName) {
        Query query;
        Update update;

        //get sequence no
        query = new Query(Criteria.where("id").is(sequenceName));
        //update the sequence no increment by 1
        update = new Update().inc("seq", 1);

        //modify in document
        DbSequence counter = mongoOperations
                .findAndModify(query,
                        update, options().returnNew(true).upsert(true),
                        DbSequence.class);

        //If the sequence number not present then return 1 or else the counter value.
        return !Objects.isNull(counter) ? counter.getSeq() : 1;
    }
}
