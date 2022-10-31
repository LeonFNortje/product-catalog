package network.rain.product.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import network.rain.product.repository.RelatedPlaceRepository;
import network.rain.product.service.RelatedPlaceService;
import network.rain.product.service.dto.RelatedPlaceDTO;
import network.rain.product.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.reactive.ResponseUtil;

/**
 * REST controller for managing {@link network.rain.product.domain.RelatedPlace}.
 */
@RestController
@RequestMapping("/api")
public class RelatedPlaceResource {

    private final Logger log = LoggerFactory.getLogger(RelatedPlaceResource.class);

    private static final String ENTITY_NAME = "relatedPlace";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RelatedPlaceService relatedPlaceService;

    private final RelatedPlaceRepository relatedPlaceRepository;

    public RelatedPlaceResource(RelatedPlaceService relatedPlaceService, RelatedPlaceRepository relatedPlaceRepository) {
        this.relatedPlaceService = relatedPlaceService;
        this.relatedPlaceRepository = relatedPlaceRepository;
    }

    /**
     * {@code POST  /related-places} : Create a new relatedPlace.
     *
     * @param relatedPlaceDTO the relatedPlaceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new relatedPlaceDTO, or with status {@code 400 (Bad Request)} if the relatedPlace has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/related-places")
    public Mono<ResponseEntity<RelatedPlaceDTO>> createRelatedPlace(@RequestBody RelatedPlaceDTO relatedPlaceDTO)
        throws URISyntaxException {
        log.debug("REST request to save RelatedPlace : {}", relatedPlaceDTO);
        if (relatedPlaceDTO.getId() != null) {
            throw new BadRequestAlertException("A new relatedPlace cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return relatedPlaceService
            .save(relatedPlaceDTO)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/related-places/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /related-places/:id} : Updates an existing relatedPlace.
     *
     * @param id the id of the relatedPlaceDTO to save.
     * @param relatedPlaceDTO the relatedPlaceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated relatedPlaceDTO,
     * or with status {@code 400 (Bad Request)} if the relatedPlaceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the relatedPlaceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/related-places/{id}")
    public Mono<ResponseEntity<RelatedPlaceDTO>> updateRelatedPlace(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody RelatedPlaceDTO relatedPlaceDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RelatedPlace : {}, {}", id, relatedPlaceDTO);
        if (relatedPlaceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, relatedPlaceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return relatedPlaceRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return relatedPlaceService
                    .update(relatedPlaceDTO)
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(result ->
                        ResponseEntity
                            .ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId()))
                            .body(result)
                    );
            });
    }

    /**
     * {@code PATCH  /related-places/:id} : Partial updates given fields of an existing relatedPlace, field will ignore if it is null
     *
     * @param id the id of the relatedPlaceDTO to save.
     * @param relatedPlaceDTO the relatedPlaceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated relatedPlaceDTO,
     * or with status {@code 400 (Bad Request)} if the relatedPlaceDTO is not valid,
     * or with status {@code 404 (Not Found)} if the relatedPlaceDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the relatedPlaceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/related-places/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<RelatedPlaceDTO>> partialUpdateRelatedPlace(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody RelatedPlaceDTO relatedPlaceDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RelatedPlace partially : {}, {}", id, relatedPlaceDTO);
        if (relatedPlaceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, relatedPlaceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return relatedPlaceRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<RelatedPlaceDTO> result = relatedPlaceService.partialUpdate(relatedPlaceDTO);

                return result
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(res ->
                        ResponseEntity
                            .ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, res.getId()))
                            .body(res)
                    );
            });
    }

    /**
     * {@code GET  /related-places} : get all the relatedPlaces.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of relatedPlaces in body.
     */
    @GetMapping("/related-places")
    public Mono<ResponseEntity<List<RelatedPlaceDTO>>> getAllRelatedPlaces(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        log.debug("REST request to get a page of RelatedPlaces");
        return relatedPlaceService
            .countAll()
            .zipWith(relatedPlaceService.findAll(pageable).collectList())
            .map(countWithEntities ->
                ResponseEntity
                    .ok()
                    .headers(
                        PaginationUtil.generatePaginationHttpHeaders(
                            UriComponentsBuilder.fromHttpRequest(request),
                            new PageImpl<>(countWithEntities.getT2(), pageable, countWithEntities.getT1())
                        )
                    )
                    .body(countWithEntities.getT2())
            );
    }

    /**
     * {@code GET  /related-places/:id} : get the "id" relatedPlace.
     *
     * @param id the id of the relatedPlaceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the relatedPlaceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/related-places/{id}")
    public Mono<ResponseEntity<RelatedPlaceDTO>> getRelatedPlace(@PathVariable String id) {
        log.debug("REST request to get RelatedPlace : {}", id);
        Mono<RelatedPlaceDTO> relatedPlaceDTO = relatedPlaceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(relatedPlaceDTO);
    }

    /**
     * {@code DELETE  /related-places/:id} : delete the "id" relatedPlace.
     *
     * @param id the id of the relatedPlaceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/related-places/{id}")
    public Mono<ResponseEntity<Void>> deleteRelatedPlace(@PathVariable String id) {
        log.debug("REST request to delete RelatedPlace : {}", id);
        return relatedPlaceService
            .delete(id)
            .then(
                Mono.just(
                    ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build()
                )
            );
    }
}
