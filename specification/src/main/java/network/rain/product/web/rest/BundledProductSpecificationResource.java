package network.rain.product.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import network.rain.product.repository.BundledProductSpecificationRepository;
import network.rain.product.service.BundledProductSpecificationService;
import network.rain.product.service.dto.BundledProductSpecificationDTO;
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
 * REST controller for managing {@link network.rain.product.domain.BundledProductSpecification}.
 */
@RestController
@RequestMapping("/api")
public class BundledProductSpecificationResource {

    private final Logger log = LoggerFactory.getLogger(BundledProductSpecificationResource.class);

    private static final String ENTITY_NAME = "specificationBundledProductSpecification";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BundledProductSpecificationService bundledProductSpecificationService;

    private final BundledProductSpecificationRepository bundledProductSpecificationRepository;

    public BundledProductSpecificationResource(
        BundledProductSpecificationService bundledProductSpecificationService,
        BundledProductSpecificationRepository bundledProductSpecificationRepository
    ) {
        this.bundledProductSpecificationService = bundledProductSpecificationService;
        this.bundledProductSpecificationRepository = bundledProductSpecificationRepository;
    }

    /**
     * {@code POST  /bundled-product-specifications} : Create a new bundledProductSpecification.
     *
     * @param bundledProductSpecificationDTO the bundledProductSpecificationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bundledProductSpecificationDTO, or with status {@code 400 (Bad Request)} if the bundledProductSpecification has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bundled-product-specifications")
    public ResponseEntity<BundledProductSpecificationDTO> createBundledProductSpecification(
        @RequestBody BundledProductSpecificationDTO bundledProductSpecificationDTO
    ) throws URISyntaxException {
        log.debug("REST request to save BundledProductSpecification : {}", bundledProductSpecificationDTO);
        if (bundledProductSpecificationDTO.getId() != null) {
            throw new BadRequestAlertException("A new bundledProductSpecification cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BundledProductSpecificationDTO result = bundledProductSpecificationService.save(bundledProductSpecificationDTO);
        return ResponseEntity
            .created(new URI("/api/bundled-product-specifications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /bundled-product-specifications/:id} : Updates an existing bundledProductSpecification.
     *
     * @param id the id of the bundledProductSpecificationDTO to save.
     * @param bundledProductSpecificationDTO the bundledProductSpecificationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bundledProductSpecificationDTO,
     * or with status {@code 400 (Bad Request)} if the bundledProductSpecificationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bundledProductSpecificationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bundled-product-specifications/{id}")
    public ResponseEntity<BundledProductSpecificationDTO> updateBundledProductSpecification(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody BundledProductSpecificationDTO bundledProductSpecificationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BundledProductSpecification : {}, {}", id, bundledProductSpecificationDTO);
        if (bundledProductSpecificationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bundledProductSpecificationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bundledProductSpecificationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BundledProductSpecificationDTO result = bundledProductSpecificationService.update(bundledProductSpecificationDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bundledProductSpecificationDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /bundled-product-specifications/:id} : Partial updates given fields of an existing bundledProductSpecification, field will ignore if it is null
     *
     * @param id the id of the bundledProductSpecificationDTO to save.
     * @param bundledProductSpecificationDTO the bundledProductSpecificationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bundledProductSpecificationDTO,
     * or with status {@code 400 (Bad Request)} if the bundledProductSpecificationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bundledProductSpecificationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bundledProductSpecificationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bundled-product-specifications/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BundledProductSpecificationDTO> partialUpdateBundledProductSpecification(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody BundledProductSpecificationDTO bundledProductSpecificationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BundledProductSpecification partially : {}, {}", id, bundledProductSpecificationDTO);
        if (bundledProductSpecificationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bundledProductSpecificationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bundledProductSpecificationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BundledProductSpecificationDTO> result = bundledProductSpecificationService.partialUpdate(bundledProductSpecificationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bundledProductSpecificationDTO.getId())
        );
    }

    /**
     * {@code GET  /bundled-product-specifications} : get all the bundledProductSpecifications.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bundledProductSpecifications in body.
     */
    @GetMapping("/bundled-product-specifications")
    public ResponseEntity<List<BundledProductSpecificationDTO>> getAllBundledProductSpecifications(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of BundledProductSpecifications");
        Page<BundledProductSpecificationDTO> page = bundledProductSpecificationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bundled-product-specifications/:id} : get the "id" bundledProductSpecification.
     *
     * @param id the id of the bundledProductSpecificationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bundledProductSpecificationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bundled-product-specifications/{id}")
    public ResponseEntity<BundledProductSpecificationDTO> getBundledProductSpecification(@PathVariable String id) {
        log.debug("REST request to get BundledProductSpecification : {}", id);
        Optional<BundledProductSpecificationDTO> bundledProductSpecificationDTO = bundledProductSpecificationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bundledProductSpecificationDTO);
    }

    /**
     * {@code DELETE  /bundled-product-specifications/:id} : delete the "id" bundledProductSpecification.
     *
     * @param id the id of the bundledProductSpecificationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bundled-product-specifications/{id}")
    public ResponseEntity<Void> deleteBundledProductSpecification(@PathVariable String id) {
        log.debug("REST request to delete BundledProductSpecification : {}", id);
        bundledProductSpecificationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
