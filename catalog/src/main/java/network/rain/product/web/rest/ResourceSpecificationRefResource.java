package network.rain.product.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import network.rain.product.repository.ResourceSpecificationRefRepository;
import network.rain.product.service.ResourceSpecificationRefService;
import network.rain.product.service.dto.ResourceSpecificationRefDTO;
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
 * REST controller for managing {@link network.rain.product.domain.ResourceSpecificationRef}.
 */
@RestController
@RequestMapping("/api")
public class ResourceSpecificationRefResource {

    private final Logger log = LoggerFactory.getLogger(ResourceSpecificationRefResource.class);

    private static final String ENTITY_NAME = "resourceSpecificationRef";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResourceSpecificationRefService resourceSpecificationRefService;

    private final ResourceSpecificationRefRepository resourceSpecificationRefRepository;

    public ResourceSpecificationRefResource(
        ResourceSpecificationRefService resourceSpecificationRefService,
        ResourceSpecificationRefRepository resourceSpecificationRefRepository
    ) {
        this.resourceSpecificationRefService = resourceSpecificationRefService;
        this.resourceSpecificationRefRepository = resourceSpecificationRefRepository;
    }

    /**
     * {@code POST  /resource-specification-refs} : Create a new resourceSpecificationRef.
     *
     * @param resourceSpecificationRefDTO the resourceSpecificationRefDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new resourceSpecificationRefDTO, or with status {@code 400 (Bad Request)} if the resourceSpecificationRef has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/resource-specification-refs")
    public Mono<ResponseEntity<ResourceSpecificationRefDTO>> createResourceSpecificationRef(
        @RequestBody ResourceSpecificationRefDTO resourceSpecificationRefDTO
    ) throws URISyntaxException {
        log.debug("REST request to save ResourceSpecificationRef : {}", resourceSpecificationRefDTO);
        if (resourceSpecificationRefDTO.getId() != null) {
            throw new BadRequestAlertException("A new resourceSpecificationRef cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return resourceSpecificationRefService
            .save(resourceSpecificationRefDTO)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/resource-specification-refs/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /resource-specification-refs/:id} : Updates an existing resourceSpecificationRef.
     *
     * @param id the id of the resourceSpecificationRefDTO to save.
     * @param resourceSpecificationRefDTO the resourceSpecificationRefDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resourceSpecificationRefDTO,
     * or with status {@code 400 (Bad Request)} if the resourceSpecificationRefDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the resourceSpecificationRefDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/resource-specification-refs/{id}")
    public Mono<ResponseEntity<ResourceSpecificationRefDTO>> updateResourceSpecificationRef(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody ResourceSpecificationRefDTO resourceSpecificationRefDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ResourceSpecificationRef : {}, {}", id, resourceSpecificationRefDTO);
        if (resourceSpecificationRefDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, resourceSpecificationRefDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return resourceSpecificationRefRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return resourceSpecificationRefService
                    .update(resourceSpecificationRefDTO)
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
     * {@code PATCH  /resource-specification-refs/:id} : Partial updates given fields of an existing resourceSpecificationRef, field will ignore if it is null
     *
     * @param id the id of the resourceSpecificationRefDTO to save.
     * @param resourceSpecificationRefDTO the resourceSpecificationRefDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resourceSpecificationRefDTO,
     * or with status {@code 400 (Bad Request)} if the resourceSpecificationRefDTO is not valid,
     * or with status {@code 404 (Not Found)} if the resourceSpecificationRefDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the resourceSpecificationRefDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/resource-specification-refs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<ResourceSpecificationRefDTO>> partialUpdateResourceSpecificationRef(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody ResourceSpecificationRefDTO resourceSpecificationRefDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ResourceSpecificationRef partially : {}, {}", id, resourceSpecificationRefDTO);
        if (resourceSpecificationRefDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, resourceSpecificationRefDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return resourceSpecificationRefRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<ResourceSpecificationRefDTO> result = resourceSpecificationRefService.partialUpdate(resourceSpecificationRefDTO);

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
     * {@code GET  /resource-specification-refs} : get all the resourceSpecificationRefs.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of resourceSpecificationRefs in body.
     */
    @GetMapping("/resource-specification-refs")
    public Mono<ResponseEntity<List<ResourceSpecificationRefDTO>>> getAllResourceSpecificationRefs(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        log.debug("REST request to get a page of ResourceSpecificationRefs");
        return resourceSpecificationRefService
            .countAll()
            .zipWith(resourceSpecificationRefService.findAll(pageable).collectList())
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
     * {@code GET  /resource-specification-refs/:id} : get the "id" resourceSpecificationRef.
     *
     * @param id the id of the resourceSpecificationRefDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the resourceSpecificationRefDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/resource-specification-refs/{id}")
    public Mono<ResponseEntity<ResourceSpecificationRefDTO>> getResourceSpecificationRef(@PathVariable String id) {
        log.debug("REST request to get ResourceSpecificationRef : {}", id);
        Mono<ResourceSpecificationRefDTO> resourceSpecificationRefDTO = resourceSpecificationRefService.findOne(id);
        return ResponseUtil.wrapOrNotFound(resourceSpecificationRefDTO);
    }

    /**
     * {@code DELETE  /resource-specification-refs/:id} : delete the "id" resourceSpecificationRef.
     *
     * @param id the id of the resourceSpecificationRefDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/resource-specification-refs/{id}")
    public Mono<ResponseEntity<Void>> deleteResourceSpecificationRef(@PathVariable String id) {
        log.debug("REST request to delete ResourceSpecificationRef : {}", id);
        return resourceSpecificationRefService
            .delete(id)
            .then(
                Mono.just(
                    ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build()
                )
            );
    }
}
