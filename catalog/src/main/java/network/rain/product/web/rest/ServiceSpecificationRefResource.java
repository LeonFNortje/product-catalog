package network.rain.product.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import network.rain.product.repository.ServiceSpecificationRefRepository;
import network.rain.product.service.ServiceSpecificationRefService;
import network.rain.product.service.dto.ServiceSpecificationRefDTO;
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
 * REST controller for managing {@link network.rain.product.domain.ServiceSpecificationRef}.
 */
@RestController
@RequestMapping("/api")
public class ServiceSpecificationRefResource {

    private final Logger log = LoggerFactory.getLogger(ServiceSpecificationRefResource.class);

    private static final String ENTITY_NAME = "serviceSpecificationRef";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceSpecificationRefService serviceSpecificationRefService;

    private final ServiceSpecificationRefRepository serviceSpecificationRefRepository;

    public ServiceSpecificationRefResource(
        ServiceSpecificationRefService serviceSpecificationRefService,
        ServiceSpecificationRefRepository serviceSpecificationRefRepository
    ) {
        this.serviceSpecificationRefService = serviceSpecificationRefService;
        this.serviceSpecificationRefRepository = serviceSpecificationRefRepository;
    }

    /**
     * {@code POST  /service-specification-refs} : Create a new serviceSpecificationRef.
     *
     * @param serviceSpecificationRefDTO the serviceSpecificationRefDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceSpecificationRefDTO, or with status {@code 400 (Bad Request)} if the serviceSpecificationRef has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/service-specification-refs")
    public Mono<ResponseEntity<ServiceSpecificationRefDTO>> createServiceSpecificationRef(
        @RequestBody ServiceSpecificationRefDTO serviceSpecificationRefDTO
    ) throws URISyntaxException {
        log.debug("REST request to save ServiceSpecificationRef : {}", serviceSpecificationRefDTO);
        if (serviceSpecificationRefDTO.getId() != null) {
            throw new BadRequestAlertException("A new serviceSpecificationRef cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return serviceSpecificationRefService
            .save(serviceSpecificationRefDTO)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/service-specification-refs/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /service-specification-refs/:id} : Updates an existing serviceSpecificationRef.
     *
     * @param id the id of the serviceSpecificationRefDTO to save.
     * @param serviceSpecificationRefDTO the serviceSpecificationRefDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceSpecificationRefDTO,
     * or with status {@code 400 (Bad Request)} if the serviceSpecificationRefDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceSpecificationRefDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/service-specification-refs/{id}")
    public Mono<ResponseEntity<ServiceSpecificationRefDTO>> updateServiceSpecificationRef(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody ServiceSpecificationRefDTO serviceSpecificationRefDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ServiceSpecificationRef : {}, {}", id, serviceSpecificationRefDTO);
        if (serviceSpecificationRefDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, serviceSpecificationRefDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return serviceSpecificationRefRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return serviceSpecificationRefService
                    .update(serviceSpecificationRefDTO)
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
     * {@code PATCH  /service-specification-refs/:id} : Partial updates given fields of an existing serviceSpecificationRef, field will ignore if it is null
     *
     * @param id the id of the serviceSpecificationRefDTO to save.
     * @param serviceSpecificationRefDTO the serviceSpecificationRefDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceSpecificationRefDTO,
     * or with status {@code 400 (Bad Request)} if the serviceSpecificationRefDTO is not valid,
     * or with status {@code 404 (Not Found)} if the serviceSpecificationRefDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the serviceSpecificationRefDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/service-specification-refs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<ServiceSpecificationRefDTO>> partialUpdateServiceSpecificationRef(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody ServiceSpecificationRefDTO serviceSpecificationRefDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ServiceSpecificationRef partially : {}, {}", id, serviceSpecificationRefDTO);
        if (serviceSpecificationRefDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, serviceSpecificationRefDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return serviceSpecificationRefRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<ServiceSpecificationRefDTO> result = serviceSpecificationRefService.partialUpdate(serviceSpecificationRefDTO);

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
     * {@code GET  /service-specification-refs} : get all the serviceSpecificationRefs.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceSpecificationRefs in body.
     */
    @GetMapping("/service-specification-refs")
    public Mono<ResponseEntity<List<ServiceSpecificationRefDTO>>> getAllServiceSpecificationRefs(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        log.debug("REST request to get a page of ServiceSpecificationRefs");
        return serviceSpecificationRefService
            .countAll()
            .zipWith(serviceSpecificationRefService.findAll(pageable).collectList())
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
     * {@code GET  /service-specification-refs/:id} : get the "id" serviceSpecificationRef.
     *
     * @param id the id of the serviceSpecificationRefDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceSpecificationRefDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/service-specification-refs/{id}")
    public Mono<ResponseEntity<ServiceSpecificationRefDTO>> getServiceSpecificationRef(@PathVariable String id) {
        log.debug("REST request to get ServiceSpecificationRef : {}", id);
        Mono<ServiceSpecificationRefDTO> serviceSpecificationRefDTO = serviceSpecificationRefService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceSpecificationRefDTO);
    }

    /**
     * {@code DELETE  /service-specification-refs/:id} : delete the "id" serviceSpecificationRef.
     *
     * @param id the id of the serviceSpecificationRefDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/service-specification-refs/{id}")
    public Mono<ResponseEntity<Void>> deleteServiceSpecificationRef(@PathVariable String id) {
        log.debug("REST request to delete ServiceSpecificationRef : {}", id);
        return serviceSpecificationRefService
            .delete(id)
            .then(
                Mono.just(
                    ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build()
                )
            );
    }
}
