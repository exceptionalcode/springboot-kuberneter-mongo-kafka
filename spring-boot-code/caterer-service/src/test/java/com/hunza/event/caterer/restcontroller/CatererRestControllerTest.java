package com.hunza.event.caterer.restcontroller;

import com.hunza.event.caterer.model.Capacity;
import com.hunza.event.caterer.model.Caterer;
import com.hunza.event.caterer.model.ContactInfo;
import com.hunza.event.caterer.model.Location;
import com.hunza.event.caterer.service.CatererService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;


/**
 * This is the test class for {@link CatererRestControllerTest}.
 * <p>
 * It will hold the unit test case for Rest controller apis.
 *
 * @author ishaan.solanki
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = CatererRestController.class)
public class CatererRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CatererService catererService;

    Caterer caterer = getCaterersObject().get(0);

    String json = "{\"name\":\"test caterer\",\"location\":{\"cityName\":\"Indore\",\"streetNameNumber\":\"b\",\"postalCode\":\"azxc\"},\"capacity\":{\"minGuest\":1,\"maxGuest\":2},\"contactInfo\":{\"phoneNumber\":\"88888\",\"mobileNumber\":\"+91-7869265829\",\"emailAddress\":\"test@somewebsite.com\"}}";

    /**
     * Test to cover addCaterer of controller layer for the API- /api/caterer
     */
    @Test
    public void addCatererTest() throws Exception {
        Mockito.when(catererService.addCaterer(Mockito.any(Caterer.class))).thenReturn(caterer);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/caterer")
                .accept(MediaType.APPLICATION_JSON).content(json)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    /**
     * Test to cover readAllCaterer of controller layer for the API- /api/caterer/all
     */
    @Test
    public void readAllCatererTest() throws Exception {
        // 200 Response
        Mockito.when(catererService.getAllCaterers()).thenReturn(getCaterersObject());
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/caterer/all")
                .accept(MediaType.APPLICATION_JSON).content(json)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());

        // 204 Response
        Mockito.when(catererService.getAllCaterers()).thenReturn(Collections.emptyList());
        RequestBuilder requestBuilder1 = MockMvcRequestBuilders.get("/api/caterer/all")
                .accept(MediaType.APPLICATION_JSON).content(json)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result1 = mockMvc.perform(requestBuilder1).andReturn();
        MockHttpServletResponse response1 = result1.getResponse();
        assertEquals(HttpStatus.NO_CONTENT.value(), response1.getStatus());
    }

    /**
     * Test to cover getCatererDetailById of controller layer for the API- /api/caterer/getDetailsById/{id}
     */
    @Test
    public void getCatererDetailByIdTest() throws Exception {
        // 200 Response
        Mockito.when(catererService.getCatererById(Mockito.anyLong())).thenReturn(Optional.of(caterer));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/caterer/getDetailsById/{id}", 1)
                .accept(MediaType.APPLICATION_JSON).content(json)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());

        // 204 Response
        Mockito.when(catererService.getCatererById(Mockito.anyLong())).thenReturn(Optional.empty());
        RequestBuilder requestBuilder1 = MockMvcRequestBuilders.get("/api/caterer/getDetailsById/{id}", 1)
                .accept(MediaType.APPLICATION_JSON).content(json)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result1 = mockMvc.perform(requestBuilder1).andReturn();
        MockHttpServletResponse response1 = result1.getResponse();
        assertEquals(HttpStatus.NO_CONTENT.value(), response1.getStatus());
    }

    /**
     * Test to cover getCatererDetailByName of controller layer for the API- api/caterer/getDetailsByName/{name}
     */
    @Test
    public void getCatererDetailByNameTest() throws Exception {
        // 200 Response
        Mockito.when(catererService.getCatererByName(Mockito.anyString())).thenReturn(Optional.of(caterer));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/caterer/getDetailsByName/{name}", "Test caterer")
                .accept(MediaType.APPLICATION_JSON).content(json)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());

        // 204 Response
        Mockito.when(catererService.getCatererByName(Mockito.anyString())).thenReturn(Optional.empty());
        RequestBuilder requestBuilder1 = MockMvcRequestBuilders.get("/api/caterer/getDetailsByName/{name}", "Test caterer")
                .accept(MediaType.APPLICATION_JSON).content(json)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result1 = mockMvc.perform(requestBuilder1).andReturn();
        MockHttpServletResponse response1 = result1.getResponse();
        assertEquals(HttpStatus.NO_CONTENT.value(), response1.getStatus());
    }

    /**
     * Test to cover getCatererByCityName of controller layer for the API- /api/caterer/city?page=1&city=test
     */
    @Test
    public void getCatererByCityNameTest() throws Exception {
        // 200 Response
        Page<Caterer> page= new PageImpl<>(getCaterersObject());
        Mockito.when(catererService.getCatererByCityName(Mockito.anyString(), Mockito.anyInt())).thenReturn(page);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/caterer/city?page=1&city=test")
                .accept(MediaType.APPLICATION_JSON).content(json)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());

        // 204 Response
        Page<Caterer> pageEmpty= new PageImpl<>(new ArrayList<>());
        Mockito.when(catererService.getCatererByCityName(Mockito.anyString(), Mockito.anyInt())).thenReturn(pageEmpty);
        RequestBuilder requestBuilder1 = MockMvcRequestBuilders.get("/api/caterer/city?page=1&city=test", "Test caterer")
                .accept(MediaType.APPLICATION_JSON).content(json)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result1 = mockMvc.perform(requestBuilder1).andReturn();
        MockHttpServletResponse response1 = result1.getResponse();
        assertEquals(HttpStatus.NO_CONTENT.value(), response1.getStatus());
    }

    /**
     * Test to cover clearCache of controller layer for the API- /api/caterer/evictCache
     */
    @Test
    public void clearCacheTest() throws Exception {
        // 200 Response
        Mockito.doNothing().when(catererService).clearCache();
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/caterer/evictCache");

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
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
