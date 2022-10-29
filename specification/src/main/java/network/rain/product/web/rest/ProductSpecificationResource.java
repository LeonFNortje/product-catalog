package network.rain.product.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import network.rain.product.repository.ProductSpecificationRepository;
import network.rain.product.service.ProductSpecificationService;
import network.rain.product.service.dto.ProductSpecificationDTO;
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
 * REST controller for managing {@link network.rain.product.domain.ProductSpecification}.
 */
@RestController
@RequestMapping("/api")
public class ProductSpecificationResource {

    private final Logger log = LoggerFactory.getLogger(ProductSpecificationResource.class);

    private static final String ENTITY_NAME = "specificationProductSpecification";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductSpecificationService productSpecificationService;

    private final ProductSpecificationRepository productSpecificationRepository;

    public ProductSpecificationResource(
        ProductSpecificationService productSpecificationService,
        ProductSpecificationRepository productSpecificationRepository
    ) {
        this.productSpecificationService = productSpecificationService;
        this.productSpecificationRepository = productSpecificationRepository;
    }

    /**
     * {@code POST  /product-specifications} : Create a new productSpecification.
     *
     * @param productSpecificationDTO the productSpecificationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productSpecificationDTO, or with status {@code 400 (Bad Request)} if the productSpecification has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-specifications")
    public ResponseEntity<ProductSpecificationDTO> createProductSpecification(@RequestBody ProductSpecificationDTO productSpecificationDTO)
        throws URISyntaxException {
        log.debug("REST request to save ProductSpecification : {}", productSpecificationDTO);
        if (productSpecificationDTO.getId() != null) {
            throw new BadRequestAlertException("A new productSpecification cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductSpecificationDTO result = productSpecificationService.save(productSpecificationDTO);
        return ResponseEntity
            .created(new URI("/api/product-specifications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /product-specifications/:id} : Updates an existing productSpecification.
     *
     * @param id the id of the productSpecificationDTO to save.
     * @param productSpecificationDTO the productSpecificationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productSpecificationDTO,
     * or with status {@code 400 (Bad Request)} if the productSpecificationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productSpecificationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-specifications/{id}")
    public ResponseEntity<ProductSpecificationDTO> updateProductSpecification(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody ProductSpecificationDTO productSpecificationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProductSpecification : {}, {}", id, productSpecificationDTO);
        if (productSpecificationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productSpecificationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productSpecificationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductSpecificationDTO result = productSpecificationService.update(productSpecificationDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productSpecificationDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /product-specifications/:id} : Partial updates given fields of an existing productSpecification, field will ignore if it is null
     *
     * @param id the id of the productSpecificationDTO to save.
     * @param productSpecificationDTO the productSpecificationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productSpecificationDTO,
     * or with status {@code 400 (Bad Request)} if the productSpecificationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the productSpecificationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the productSpecificationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/product-specifications/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProductSpecificationDTO> partialUpdateProductSpecification(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody ProductSpecificationDTO productSpecificationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductSpecification partially : {}, {}", id, productSpecificationDTO);
        if (productSpecificationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productSpecificationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productSpecificationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductSpecificationDTO> result = productSpecificationService.partialUpdate(productSpecificationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productSpecificationDTO.getId())
        );
    }

    /**
     * {@code GET  /product-specifications} : get all the productSpecifications.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productSpecifications in body.
     */
    @GetMapping("/product-specifications")
    public ResponseEntity<List<ProductSpecificationDTO>> getAllProductSpecifications(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of ProductSpecifications");
        Page<ProductSpecificationDTO> page = productSpecificationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-specifications/:id} : get the "id" productSpecification.
     *
     * @param id the id of the productSpecificationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productSpecificationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-specifications/{id}")
    public ResponseEntity<ProductSpecificationDTO> getProductSpecification(@PathVariable String id) {
        log.debug("REST request to get ProductSpecification : {}", id);
        Optional<ProductSpecificationDTO> productSpecificationDTO = productSpecificationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productSpecificationDTO);
    }

    /**
     * {@code DELETE  /product-specifications/:id} : delete the "id" productSpecification.
     *
     * @param id the id of the productSpecificationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-specifications/{id}")
    public ResponseEntity<Void> deleteProductSpecification(@PathVariable String id) {
        log.debug("REST request to delete ProductSpecification : {}", id);
        productSpecificationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
