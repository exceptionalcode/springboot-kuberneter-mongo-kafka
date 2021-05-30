package com.hunza.event.caterer.restcontroller;

import com.hunza.event.caterer.model.Caterer;
import com.hunza.event.caterer.service.CatererService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author ishaan.solanki
 * <p>
 * Class {@link CatererRestController} responsible REST endpoints for caterer application system.
 * <p>This rest service layer will iteract with service layer and produces json data against an endpoint.</p>
 */
@RestController
@RequestMapping("/api/caterer")
@Api(value = "API to search and add Caterers from a Caterer Repository by different search parameters", produces = "application/json")
public class CatererRestController {

    /**
     * logger for {@link Logger}
     */
    private static final Logger logger = LoggerFactory.getLogger(CatererRestController.class);

    @Autowired
    private CatererService catererService;

    @ApiOperation(value = "Add Caterer", produces = "application/json")
    @PostMapping
    public ResponseEntity<Caterer> addCaterer(@Valid @RequestBody Caterer caterer) {
        Caterer catererAdded = catererService.addCaterer(caterer);
        logger.info("Added caterer in db with response {} ", caterer);
        return ResponseEntity.ok(catererAdded);
    }

    @ApiOperation(value = "Search all Caterers", produces = "application/json")
    @GetMapping("/all")
    public ResponseEntity<Collection<Caterer>> readAllCaterer() {
        Collection<Caterer> caterers = catererService.getAllCaterers();
        if (!caterers.isEmpty()) {
            logger.info("All the list of caterers are {}", caterers);
            return ResponseEntity.ok(caterers);
        }
        logger.info("No caterer found");
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Search Caterer by Id", produces = "application/json")
    @GetMapping("getDetailsById/{id}")
    public ResponseEntity<Caterer> getCatererDetailById(
            @ApiParam(name = "id",
                    value = "filtering id",
                    required = true) @PathVariable Long id) {
        Optional<Caterer> catererById = catererService.getCatererById(id);
        logger.info("Caterer by id {} presented with details {}", id, catererById);
        if (catererById.isPresent()) {
            EntityModel<Caterer> resources = EntityModel.of(catererById.get());
            resources.add(linkTo(methodOn(this.getClass()).readAllCaterer()).withRel("all-caterer"));
            resources.add(linkTo(methodOn(this.getClass()).getCatererDetailByName(catererById.get().getName())).withRel("get-by-name"));

            return new ResponseEntity(resources, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "Search Caterer by name", produces = "application/json")
    @GetMapping("getDetailsByName/{name}")
    public ResponseEntity<Caterer> getCatererDetailByName(
            @ApiParam(name = "name",
                    value = "filtering name",
                    required = true) @PathVariable String name) {
        Optional<Caterer> catererByName = catererService.getCatererByName(name);
        logger.info("Caterer by name {} presented with details {}", name, catererByName);
        if (catererByName.isPresent()) {
            EntityModel<Caterer> resources = EntityModel.of(catererByName.get());
            resources.add(linkTo(methodOn(this.getClass()).readAllCaterer()).withRel("all-caterer"));
            resources.add(linkTo(methodOn(this.getClass()).getCatererDetailById(catererByName.get().getId())).withRel("get-by-id"));

            return new ResponseEntity(resources, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "Search Caterer by City", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "city", value = "city name", required = true, dataType = "String"),
            @ApiImplicitParam(name = "page", value = "page number", required = true, dataType = "Integer")})
    @GetMapping("/city")
    public ResponseEntity<Page<Caterer>> getCatererByCityName(@RequestParam(name = "city") String cityName, @RequestParam(name = "page") Integer page) {
        Page<Caterer> caterersByCityName = catererService.getCatererByCityName(cityName, page);
        logger.info("Caterers by city are {}", caterersByCityName.getContent());
        if (!caterersByCityName.getContent().isEmpty()) {
            return ResponseEntity.ok(caterersByCityName);
        }
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "API to evict existing cached entries")
    @PostMapping("/evictCache")
    public void clearCache() {
        logger.info("clear all the cache entries in caterer cache store");
        catererService.clearCache();
    }

}
