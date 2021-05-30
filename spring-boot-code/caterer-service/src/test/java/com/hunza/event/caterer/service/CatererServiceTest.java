package com.hunza.event.caterer.service;

import com.hunza.event.caterer.model.Capacity;
import com.hunza.event.caterer.model.Caterer;
import com.hunza.event.caterer.model.ContactInfo;
import com.hunza.event.caterer.model.Location;
import com.hunza.event.caterer.repository.CatererRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This is the test class for {@link CatererService}.
 * <p>
 * It will hold the unit test case for business logic.
 *
 * @author ishaan.solanki
 */
@RunWith(MockitoJUnitRunner.class)
public class CatererServiceTest {

    @InjectMocks
    private CatererService catererService;

    /**
     * Before test executes adds value to @value placeholder
     */
    @Before
    public void setUp() {
        ReflectionTestUtils.setField(catererService, "pageSize", 5);
    }

    @Mock
    private CatererRepository catererRepository;

    @Mock
    private KafkaProducerService kafkaProducerService;

    @Mock
    private MongoOperations mongoOperations;

    /**
     * Test to cover getAllCaterers of service layer
     */
    @Test
    public void getAllCaterersTest() {
        Mockito.when(catererRepository.findAll()).thenReturn(getCaterersObject());
        Assert.assertNotNull(catererService.getAllCaterers());
    }

    /**
     * Test to cover getCatererById of service layer
     */
    @Test
    public void getCatererByIdTest() {
        //When caterer object is not empty
        Optional<Caterer> catererOptional = Optional.of(getCaterersObject().get(0));
        Mockito.when(catererRepository.findById(1L)).thenReturn(catererOptional);
        Assert.assertNotNull(catererService.getCatererById(1L));

        //When caterer object is empty
        Optional<Caterer> catererOptional1 = Optional.of(new Caterer());
        Mockito.when(catererRepository.findById(2L)).thenReturn(catererOptional1);
        Assert.assertEquals(catererOptional1, catererService.getCatererById(2L));
    }

    /**
     * Test to cover getCatererByName of service layer
     */
    @Test
    public void getCatererByNameTest() {
        //When caterer object is not empty
        Optional<Caterer> catererOptional = Optional.of(getCaterersObject().get(0));
        Mockito.when(catererRepository.findByName("Test")).thenReturn(catererOptional);
        Assert.assertNotNull(catererService.getCatererByName("Test"));

        //When caterer object is empty
        Optional<Caterer> catererOptional1 = Optional.of(new Caterer());
        Mockito.when(catererRepository.findByName("Test")).thenReturn(catererOptional1);
        Assert.assertEquals(catererOptional1, catererService.getCatererByName("Test"));
    }

    /**
     * Test to cover getCatererByCity of service layer
     */
    @Test
    public void getCatererByCityNameTest() {
        Page<Caterer> page = new PageImpl<>(getCaterersObject());
        Mockito.when(catererRepository.findCatererByLocation_CityName(Mockito.anyString(), Mockito.any(Pageable.class))).thenReturn(page);
        Assert.assertNotNull(catererService.getCatererByCityName("Tesy city", 5));
    }

    /**
     * Test to cover addCaterer of service layer
     */
    @Test
    public void addCatererTest() {
        //When caterer by name is already present in db, update scenerio.
        Mockito.when(catererRepository.findByName(Mockito.anyString())).thenReturn(Optional.of(getCaterersObject().get(0)));
        Mockito.when(catererRepository.save(getCaterersObject().get(0))).thenReturn(getCaterersObject().get(0));
        Assert.assertNotNull(catererService.addCaterer(getCaterersObject().get(0)));

        //When caterer by name is not already present in db, save scenerio.
        Mockito.when(catererRepository.findByName(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(catererRepository.save(getCaterersObject().get(0))).thenReturn(getCaterersObject().get(0));
        Assert.assertNotNull(catererService.addCaterer(getCaterersObject().get(0)));

        //When caterer by name is not already present in db, save respond with null so no kafka producer call.
        Mockito.when(catererRepository.findByName(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(catererRepository.save(getCaterersObject().get(0))).thenReturn(null);
        Assert.assertNull(catererService.addCaterer(getCaterersObject().get(0)));
    }

    /**
     * Test to cover clearCache of service layer
     */
    @Test
    public void clearCacheTest() {
        catererService.clearCache();
    }

    /**
     * Method to generate List of {@link Caterer} Object
     *
     * @return {@link List} of {@link Caterer}
     */
    private List<Caterer> getCaterersObject() {
        List<Caterer> caterers = new ArrayList<>();
        Caterer caterer = new Caterer();
        caterer.setId(1L);
        caterer.setName("Test caterer");

        Capacity capacity = new Capacity();
        capacity.setMaxGuest(2);
        capacity.setMinGuest(1);
        caterer.setCapacity(capacity);

        Location location = new Location();
        location.setCityName("London");
        location.setPostalCode("L45211");
        location.setStreetNameNumber("London street G21");
        caterer.setLocation(location);

        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setEmailAddress("xyz@test.com");
        contactInfo.setMobileNumber("+94-212122212");
        contactInfo.setPhoneNumber("0555-2929121");
        caterer.setContactInfo(contactInfo);

        caterers.add(caterer);
        return caterers;
    }
}
