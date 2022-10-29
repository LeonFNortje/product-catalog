package network.rain.product.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import network.rain.product.repository.TargetProductSchemaRepository;
import network.rain.product.service.TargetProductSchemaService;
import network.rain.product.service.dto.TargetProductSchemaDTO;
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
 * REST controller for managing {@link network.rain.product.domain.TargetProductSchema}.
 */
@RestController
@RequestMapping("/api")
public class TargetProductSchemaResource {

    private final Logger log = LoggerFactory.getLogger(TargetProductSchemaResource.class);

    private static final String ENTITY_NAME = "targetProductSchema";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TargetProductSchemaService targetProductSchemaService;

    private final TargetProductSchemaRepository targetProductSchemaRepository;

    public TargetProductSchemaResource(
        TargetProductSchemaService targetProductSchemaService,
        TargetProductSchemaRepository targetProductSchemaRepository
    ) {
        this.targetProductSchemaService = targetProductSchemaService;
        this.targetProductSchemaRepository = targetProductSchemaRepository;
    }

    /**
     * {@code POST  /target-product-schemas} : Create a new targetProductSchema.
     *
     * @param targetProductSchemaDTO the targetProductSchemaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new targetProductSchemaDTO, or with status {@code 400 (Bad Request)} if the targetProductSchema has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/target-product-schemas")
    public Mono<ResponseEntity<TargetProductSchemaDTO>> createTargetProductSchema(
        @RequestBody TargetProductSchemaDTO targetProductSchemaDTO
    ) throws URISyntaxException {
        log.debug("REST request to save TargetProductSchema : {}", targetProductSchemaDTO);
        if (targetProductSchemaDTO.getId() != null) {
            throw new BadRequestAlertException("A new targetProductSchema cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return targetProductSchemaService
            .save(targetProductSchemaDTO)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/target-product-schemas/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /target-product-schemas/:id} : Updates an existing targetProductSchema.
     *
     * @param id the id of the targetProductSchemaDTO to save.
     * @param targetProductSchemaDTO the targetProductSchemaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated targetProductSchemaDTO,
     * or with status {@code 400 (Bad Request)} if the targetProductSchemaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the targetProductSchemaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/target-product-schemas/{id}")
    public Mono<ResponseEntity<TargetProductSchemaDTO>> updateTargetProductSchema(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TargetProductSchemaDTO targetProductSchemaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TargetProductSchema : {}, {}", id, targetProductSchemaDTO);
        if (targetProductSchemaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, targetProductSchemaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return targetProductSchemaRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return targetProductSchemaService
                    .update(targetProductSchemaDTO)
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(result ->
                        ResponseEntity
                            .ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                            .body(result)
                    );
            });
    }

    /**
     * {@code PATCH  /target-product-schemas/:id} : Partial updates given fields of an existing targetProductSchema, field will ignore if it is null
     *
     * @param id the id of the targetProductSchemaDTO to save.
     * @param targetProductSchemaDTO the targetProductSchemaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated targetProductSchemaDTO,
     * or with status {@code 400 (Bad Request)} if the targetProductSchemaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the targetProductSchemaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the targetProductSchemaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/target-product-schemas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<TargetProductSchemaDTO>> partialUpdateTargetProductSchema(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TargetProductSchemaDTO targetProductSchemaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TargetProductSchema partially : {}, {}", id, targetProductSchemaDTO);
        if (targetProductSchemaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, targetProductSchemaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return targetProductSchemaRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<TargetProductSchemaDTO> result = targetProductSchemaService.partialUpdate(targetProductSchemaDTO);

                return result
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(res ->
                        ResponseEntity
                            .ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, res.getId().toString()))
                            .body(res)
                    );
            });
    }

    /**
     * {@code GET  /target-product-schemas} : get all the targetProductSchemas.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of targetProductSchemas in body.
     */
    @GetMapping("/target-product-schemas")
    public Mono<ResponseEntity<List<TargetProductSchemaDTO>>> getAllTargetProductSchemas(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        log.debug("REST request to get a page of TargetProductSchemas");
        return targetProductSchemaService
            .countAll()
            .zipWith(targetProductSchemaService.findAll(pageable).collectList())
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
     * {@code GET  /target-product-schemas/:id} : get the "id" targetProductSchema.
     *
     * @param id the id of the targetProductSchemaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the targetProductSchemaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/target-product-schemas/{id}")
    public Mono<ResponseEntity<TargetProductSchemaDTO>> getTargetProductSchema(@PathVariable Long id) {
        log.debug("REST request to get TargetProductSchema : {}", id);
        Mono<TargetProductSchemaDTO> targetProductSchemaDTO = targetProductSchemaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(targetProductSchemaDTO);
    }

    /**
     * {@code DELETE  /target-product-schemas/:id} : delete the "id" targetProductSchema.
     *
     * @param id the id of the targetProductSchemaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/target-product-schemas/{id}")
    public Mono<ResponseEntity<Void>> deleteTargetProductSchema(@PathVariable Long id) {
        log.debug("REST request to delete TargetProductSchema : {}", id);
        return targetProductSchemaService
            .delete(id)
            .then(
                Mono.just(
                    ResponseEntity
                        .noContent()
                        .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                        .build()
                )
            );
    }
}
