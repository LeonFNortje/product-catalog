package network.rain.product.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import network.rain.product.repository.RelatedPartyRepository;
import network.rain.product.service.RelatedPartyService;
import network.rain.product.service.dto.RelatedPartyDTO;
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
 * REST controller for managing {@link network.rain.product.domain.RelatedParty}.
 */
@RestController
@RequestMapping("/api")
public class RelatedPartyResource {

    private final Logger log = LoggerFactory.getLogger(RelatedPartyResource.class);

    private static final String ENTITY_NAME = "relatedParty";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RelatedPartyService relatedPartyService;

    private final RelatedPartyRepository relatedPartyRepository;

    public RelatedPartyResource(RelatedPartyService relatedPartyService, RelatedPartyRepository relatedPartyRepository) {
        this.relatedPartyService = relatedPartyService;
        this.relatedPartyRepository = relatedPartyRepository;
    }

    /**
     * {@code POST  /related-parties} : Create a new relatedParty.
     *
     * @param relatedPartyDTO the relatedPartyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new relatedPartyDTO, or with status {@code 400 (Bad Request)} if the relatedParty has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/related-parties")
    public Mono<ResponseEntity<RelatedPartyDTO>> createRelatedParty(@RequestBody RelatedPartyDTO relatedPartyDTO)
        throws URISyntaxException {
        log.debug("REST request to save RelatedParty : {}", relatedPartyDTO);
        if (relatedPartyDTO.getId() != null) {
            throw new BadRequestAlertException("A new relatedParty cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return relatedPartyService
            .save(relatedPartyDTO)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/related-parties/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /related-parties/:id} : Updates an existing relatedParty.
     *
     * @param id the id of the relatedPartyDTO to save.
     * @param relatedPartyDTO the relatedPartyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated relatedPartyDTO,
     * or with status {@code 400 (Bad Request)} if the relatedPartyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the relatedPartyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/related-parties/{id}")
    public Mono<ResponseEntity<RelatedPartyDTO>> updateRelatedParty(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody RelatedPartyDTO relatedPartyDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RelatedParty : {}, {}", id, relatedPartyDTO);
        if (relatedPartyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, relatedPartyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return relatedPartyRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return relatedPartyService
                    .update(relatedPartyDTO)
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
     * {@code PATCH  /related-parties/:id} : Partial updates given fields of an existing relatedParty, field will ignore if it is null
     *
     * @param id the id of the relatedPartyDTO to save.
     * @param relatedPartyDTO the relatedPartyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated relatedPartyDTO,
     * or with status {@code 400 (Bad Request)} if the relatedPartyDTO is not valid,
     * or with status {@code 404 (Not Found)} if the relatedPartyDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the relatedPartyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/related-parties/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<RelatedPartyDTO>> partialUpdateRelatedParty(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody RelatedPartyDTO relatedPartyDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RelatedParty partially : {}, {}", id, relatedPartyDTO);
        if (relatedPartyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, relatedPartyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return relatedPartyRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<RelatedPartyDTO> result = relatedPartyService.partialUpdate(relatedPartyDTO);

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
     * {@code GET  /related-parties} : get all the relatedParties.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of relatedParties in body.
     */
    @GetMapping("/related-parties")
    public Mono<ResponseEntity<List<RelatedPartyDTO>>> getAllRelatedParties(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        log.debug("REST request to get a page of RelatedParties");
        return relatedPartyService
            .countAll()
            .zipWith(relatedPartyService.findAll(pageable).collectList())
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
     * {@code GET  /related-parties/:id} : get the "id" relatedParty.
     *
     * @param id the id of the relatedPartyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the relatedPartyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/related-parties/{id}")
    public Mono<ResponseEntity<RelatedPartyDTO>> getRelatedParty(@PathVariable String id) {
        log.debug("REST request to get RelatedParty : {}", id);
        Mono<RelatedPartyDTO> relatedPartyDTO = relatedPartyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(relatedPartyDTO);
    }

    /**
     * {@code DELETE  /related-parties/:id} : delete the "id" relatedParty.
     *
     * @param id the id of the relatedPartyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/related-parties/{id}")
    public Mono<ResponseEntity<Void>> deleteRelatedParty(@PathVariable String id) {
        log.debug("REST request to delete RelatedParty : {}", id);
        return relatedPartyService
            .delete(id)
            .then(
                Mono.just(
                    ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build()
                )
            );
    }
}
