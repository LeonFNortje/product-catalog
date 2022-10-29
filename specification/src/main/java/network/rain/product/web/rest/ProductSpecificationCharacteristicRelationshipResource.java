package network.rain.product.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import network.rain.product.repository.ProductSpecificationCharacteristicRelationshipRepository;
import network.rain.product.service.ProductSpecificationCharacteristicRelationshipService;
import network.rain.product.service.dto.ProductSpecificationCharacteristicRelationshipDTO;
import network.rain.product.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link network.rain.product.domain.ProductSpecificationCharacteristicRelationship}.
 */
@RestController
@RequestMapping("/api")
public class ProductSpecificationCharacteristicRelationshipResource {

    private final Logger log = LoggerFactory.getLogger(ProductSpecificationCharacteristicRelationshipResource.class);

    private static final String ENTITY_NAME = "specificationProductSpecificationCharacteristicRelationship";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductSpecificationCharacteristicRelationshipService productSpecificationCharacteristicRelationshipService;

    private final ProductSpecificationCharacteristicRelationshipRepository productSpecificationCharacteristicRelationshipRepository;

    public ProductSpecificationCharacteristicRelationshipResource(
        ProductSpecificationCharacteristicRelationshipService productSpecificationCharacteristicRelationshipService,
        ProductSpecificationCharacteristicRelationshipRepository productSpecificationCharacteristicRelationshipRepository
    ) {
        this.productSpecificationCharacteristicRelationshipService = productSpecificationCharacteristicRelationshipService;
        this.productSpecificationCharacteristicRelationshipRepository = productSpecificationCharacteristicRelationshipRepository;
    }

    /**
     * {@code POST  /product-specification-characteristic-relationships} : Create a new productSpecificationCharacteristicRelationship.
     *
     * @param productSpecificationCharacteristicRelationshipDTO the productSpecificationCharacteristicRelationshipDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productSpecificationCharacteristicRelationshipDTO, or with status {@code 400 (Bad Request)} if the productSpecificationCharacteristicRelationship has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-specification-characteristic-relationships")
    public ResponseEntity<ProductSpecificationCharacteristicRelationshipDTO> createProductSpecificationCharacteristicRelationship(
        @RequestBody ProductSpecificationCharacteristicRelationshipDTO productSpecificationCharacteristicRelationshipDTO
    ) throws URISyntaxException {
        log.debug(
            "REST request to save ProductSpecificationCharacteristicRelationship : {}",
            productSpecificationCharacteristicRelationshipDTO
        );
        if (productSpecificationCharacteristicRelationshipDTO.getId() != null) {
            throw new BadRequestAlertException(
                "A new productSpecificationCharacteristicRelationship cannot already have an ID",
                ENTITY_NAME,
                "idexists"
            );
        }
        ProductSpecificationCharacteristicRelationshipDTO result = productSpecificationCharacteristicRelationshipService.save(
            productSpecificationCharacteristicRelationshipDTO
        );
        return ResponseEntity
            .created(new URI("/api/product-specification-characteristic-relationships/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /product-specification-characteristic-relationships/:id} : Updates an existing productSpecificationCharacteristicRelationship.
     *
     * @param id the id of the productSpecificationCharacteristicRelationshipDTO to save.
     * @param productSpecificationCharacteristicRelationshipDTO the productSpecificationCharacteristicRelationshipDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productSpecificationCharacteristicRelationshipDTO,
     * or with status {@code 400 (Bad Request)} if the productSpecificationCharacteristicRelationshipDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productSpecificationCharacteristicRelationshipDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-specification-characteristic-relationships/{id}")
    public ResponseEntity<ProductSpecificationCharacteristicRelationshipDTO> updateProductSpecificationCharacteristicRelationship(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody ProductSpecificationCharacteristicRelationshipDTO productSpecificationCharacteristicRelationshipDTO
    ) throws URISyntaxException {
        log.debug(
            "REST request to update ProductSpecificationCharacteristicRelationship : {}, {}",
            id,
            productSpecificationCharacteristicRelationshipDTO
        );
        if (productSpecificationCharacteristicRelationshipDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productSpecificationCharacteristicRelationshipDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productSpecificationCharacteristicRelationshipRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductSpecificationCharacteristicRelationshipDTO result = productSpecificationCharacteristicRelationshipService.update(
            productSpecificationCharacteristicRelationshipDTO
        );
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    productSpecificationCharacteristicRelationshipDTO.getId()
                )
            )
            .body(result);
    }

    /**
     * {@code PATCH  /product-specification-characteristic-relationships/:id} : Partial updates given fields of an existing productSpecificationCharacteristicRelationship, field will ignore if it is null
     *
     * @param id the id of the productSpecificationCharacteristicRelationshipDTO to save.
     * @param productSpecificationCharacteristicRelationshipDTO the productSpecificationCharacteristicRelationshipDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productSpecificationCharacteristicRelationshipDTO,
     * or with status {@code 400 (Bad Request)} if the productSpecificationCharacteristicRelationshipDTO is not valid,
     * or with status {@code 404 (Not Found)} if the productSpecificationCharacteristicRelationshipDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the productSpecificationCharacteristicRelationshipDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(
        value = "/product-specification-characteristic-relationships/{id}",
        consumes = { "application/json", "application/merge-patch+json" }
    )
    public ResponseEntity<ProductSpecificationCharacteristicRelationshipDTO> partialUpdateProductSpecificationCharacteristicRelationship(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody ProductSpecificationCharacteristicRelationshipDTO productSpecificationCharacteristicRelationshipDTO
    ) throws URISyntaxException {
        log.debug(
            "REST request to partial update ProductSpecificationCharacteristicRelationship partially : {}, {}",
            id,
            productSpecificationCharacteristicRelationshipDTO
        );
        if (productSpecificationCharacteristicRelationshipDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productSpecificationCharacteristicRelationshipDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productSpecificationCharacteristicRelationshipRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductSpecificationCharacteristicRelationshipDTO> result = productSpecificationCharacteristicRelationshipService.partialUpdate(
            productSpecificationCharacteristicRelationshipDTO
        );

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(
                applicationName,
                true,
                ENTITY_NAME,
                productSpecificationCharacteristicRelationshipDTO.getId()
            )
        );
    }

    /**
     * {@code GET  /product-specification-characteristic-relationships} : get all the productSpecificationCharacteristicRelationships.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productSpecificationCharacteristicRelationships in body.
     */
    @GetMapping("/product-specification-characteristic-relationships")
    public ResponseEntity<List<ProductSpecificationCharacteristicRelationshipDTO>> getAllProductSpecificationCharacteristicRelationships(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of ProductSpecificationCharacteristicRelationships");
        Page<ProductSpecificationCharacteristicRelationshipDTO> page = productSpecificationCharacteristicRelationshipService.findAll(
            pageable
        );
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-specification-characteristic-relationships/:id} : get the "id" productSpecificationCharacteristicRelationship.
     *
     * @param id the id of the productSpecificationCharacteristicRelationshipDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productSpecificationCharacteristicRelationshipDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-specification-characteristic-relationships/{id}")
    public ResponseEntity<ProductSpecificationCharacteristicRelationshipDTO> getProductSpecificationCharacteristicRelationship(
        @PathVariable String id
    ) {
        log.debug("REST request to get ProductSpecificationCharacteristicRelationship : {}", id);
        Optional<ProductSpecificationCharacteristicRelationshipDTO> productSpecificationCharacteristicRelationshipDTO = productSpecificationCharacteristicRelationshipService.findOne(
            id
        );
        return ResponseUtil.wrapOrNotFound(productSpecificationCharacteristicRelationshipDTO);
    }

    /**
     * {@code DELETE  /product-specification-characteristic-relationships/:id} : delete the "id" productSpecificationCharacteristicRelationship.
     *
     * @param id the id of the productSpecificationCharacteristicRelationshipDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-specification-characteristic-relationships/{id}")
    public ResponseEntity<Void> deleteProductSpecificationCharacteristicRelationship(@PathVariable String id) {
        log.debug("REST request to delete ProductSpecificationCharacteristicRelationship : {}", id);
        productSpecificationCharacteristicRelationshipService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}