package network.rain.product.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import network.rain.product.repository.ProductSpecificationRelationshipRepository;
import network.rain.product.service.ProductSpecificationRelationshipService;
import network.rain.product.service.dto.ProductSpecificationRelationshipDTO;
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
 * REST controller for managing {@link network.rain.product.domain.ProductSpecificationRelationship}.
 */
@RestController
@RequestMapping("/api")
public class ProductSpecificationRelationshipResource {

    private final Logger log = LoggerFactory.getLogger(ProductSpecificationRelationshipResource.class);

    private static final String ENTITY_NAME = "productSpecificationRelationship";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductSpecificationRelationshipService productSpecificationRelationshipService;

    private final ProductSpecificationRelationshipRepository productSpecificationRelationshipRepository;

    public ProductSpecificationRelationshipResource(
        ProductSpecificationRelationshipService productSpecificationRelationshipService,
        ProductSpecificationRelationshipRepository productSpecificationRelationshipRepository
    ) {
        this.productSpecificationRelationshipService = productSpecificationRelationshipService;
        this.productSpecificationRelationshipRepository = productSpecificationRelationshipRepository;
    }

    /**
     * {@code POST  /product-specification-relationships} : Create a new productSpecificationRelationship.
     *
     * @param productSpecificationRelationshipDTO the productSpecificationRelationshipDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productSpecificationRelationshipDTO, or with status {@code 400 (Bad Request)} if the productSpecificationRelationship has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-specification-relationships")
    public Mono<ResponseEntity<ProductSpecificationRelationshipDTO>> createProductSpecificationRelationship(
        @RequestBody ProductSpecificationRelationshipDTO productSpecificationRelationshipDTO
    ) throws URISyntaxException {
        log.debug("REST request to save ProductSpecificationRelationship : {}", productSpecificationRelationshipDTO);
        if (productSpecificationRelationshipDTO.getId() != null) {
            throw new BadRequestAlertException("A new productSpecificationRelationship cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return productSpecificationRelationshipService
            .save(productSpecificationRelationshipDTO)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/product-specification-relationships/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /product-specification-relationships/:id} : Updates an existing productSpecificationRelationship.
     *
     * @param id the id of the productSpecificationRelationshipDTO to save.
     * @param productSpecificationRelationshipDTO the productSpecificationRelationshipDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productSpecificationRelationshipDTO,
     * or with status {@code 400 (Bad Request)} if the productSpecificationRelationshipDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productSpecificationRelationshipDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-specification-relationships/{id}")
    public Mono<ResponseEntity<ProductSpecificationRelationshipDTO>> updateProductSpecificationRelationship(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody ProductSpecificationRelationshipDTO productSpecificationRelationshipDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProductSpecificationRelationship : {}, {}", id, productSpecificationRelationshipDTO);
        if (productSpecificationRelationshipDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productSpecificationRelationshipDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return productSpecificationRelationshipRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return productSpecificationRelationshipService
                    .update(productSpecificationRelationshipDTO)
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
     * {@code PATCH  /product-specification-relationships/:id} : Partial updates given fields of an existing productSpecificationRelationship, field will ignore if it is null
     *
     * @param id the id of the productSpecificationRelationshipDTO to save.
     * @param productSpecificationRelationshipDTO the productSpecificationRelationshipDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productSpecificationRelationshipDTO,
     * or with status {@code 400 (Bad Request)} if the productSpecificationRelationshipDTO is not valid,
     * or with status {@code 404 (Not Found)} if the productSpecificationRelationshipDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the productSpecificationRelationshipDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/product-specification-relationships/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<ProductSpecificationRelationshipDTO>> partialUpdateProductSpecificationRelationship(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody ProductSpecificationRelationshipDTO productSpecificationRelationshipDTO
    ) throws URISyntaxException {
        log.debug(
            "REST request to partial update ProductSpecificationRelationship partially : {}, {}",
            id,
            productSpecificationRelationshipDTO
        );
        if (productSpecificationRelationshipDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productSpecificationRelationshipDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return productSpecificationRelationshipRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<ProductSpecificationRelationshipDTO> result = productSpecificationRelationshipService.partialUpdate(
                    productSpecificationRelationshipDTO
                );

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
     * {@code GET  /product-specification-relationships} : get all the productSpecificationRelationships.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productSpecificationRelationships in body.
     */
    @GetMapping("/product-specification-relationships")
    public Mono<ResponseEntity<List<ProductSpecificationRelationshipDTO>>> getAllProductSpecificationRelationships(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        log.debug("REST request to get a page of ProductSpecificationRelationships");
        return productSpecificationRelationshipService
            .countAll()
            .zipWith(productSpecificationRelationshipService.findAll(pageable).collectList())
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
     * {@code GET  /product-specification-relationships/:id} : get the "id" productSpecificationRelationship.
     *
     * @param id the id of the productSpecificationRelationshipDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productSpecificationRelationshipDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-specification-relationships/{id}")
    public Mono<ResponseEntity<ProductSpecificationRelationshipDTO>> getProductSpecificationRelationship(@PathVariable String id) {
        log.debug("REST request to get ProductSpecificationRelationship : {}", id);
        Mono<ProductSpecificationRelationshipDTO> productSpecificationRelationshipDTO = productSpecificationRelationshipService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productSpecificationRelationshipDTO);
    }

    /**
     * {@code DELETE  /product-specification-relationships/:id} : delete the "id" productSpecificationRelationship.
     *
     * @param id the id of the productSpecificationRelationshipDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-specification-relationships/{id}")
    public Mono<ResponseEntity<Void>> deleteProductSpecificationRelationship(@PathVariable String id) {
        log.debug("REST request to delete ProductSpecificationRelationship : {}", id);
        return productSpecificationRelationshipService
            .delete(id)
            .then(
                Mono.just(
                    ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build()
                )
            );
    }
}
