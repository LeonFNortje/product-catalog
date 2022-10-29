package network.rain.product.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import network.rain.product.repository.ProductSpecificationCharacteristicRepository;
import network.rain.product.service.ProductSpecificationCharacteristicService;
import network.rain.product.service.dto.ProductSpecificationCharacteristicDTO;
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
 * REST controller for managing {@link network.rain.product.domain.ProductSpecificationCharacteristic}.
 */
@RestController
@RequestMapping("/api")
public class ProductSpecificationCharacteristicResource {

    private final Logger log = LoggerFactory.getLogger(ProductSpecificationCharacteristicResource.class);

    private static final String ENTITY_NAME = "specificationProductSpecificationCharacteristic";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductSpecificationCharacteristicService productSpecificationCharacteristicService;

    private final ProductSpecificationCharacteristicRepository productSpecificationCharacteristicRepository;

    public ProductSpecificationCharacteristicResource(
        ProductSpecificationCharacteristicService productSpecificationCharacteristicService,
        ProductSpecificationCharacteristicRepository productSpecificationCharacteristicRepository
    ) {
        this.productSpecificationCharacteristicService = productSpecificationCharacteristicService;
        this.productSpecificationCharacteristicRepository = productSpecificationCharacteristicRepository;
    }

    /**
     * {@code POST  /product-specification-characteristics} : Create a new productSpecificationCharacteristic.
     *
     * @param productSpecificationCharacteristicDTO the productSpecificationCharacteristicDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productSpecificationCharacteristicDTO, or with status {@code 400 (Bad Request)} if the productSpecificationCharacteristic has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-specification-characteristics")
    public ResponseEntity<ProductSpecificationCharacteristicDTO> createProductSpecificationCharacteristic(
        @RequestBody ProductSpecificationCharacteristicDTO productSpecificationCharacteristicDTO
    ) throws URISyntaxException {
        log.debug("REST request to save ProductSpecificationCharacteristic : {}", productSpecificationCharacteristicDTO);
        if (productSpecificationCharacteristicDTO.getId() != null) {
            throw new BadRequestAlertException(
                "A new productSpecificationCharacteristic cannot already have an ID",
                ENTITY_NAME,
                "idexists"
            );
        }
        ProductSpecificationCharacteristicDTO result = productSpecificationCharacteristicService.save(
            productSpecificationCharacteristicDTO
        );
        return ResponseEntity
            .created(new URI("/api/product-specification-characteristics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /product-specification-characteristics/:id} : Updates an existing productSpecificationCharacteristic.
     *
     * @param id the id of the productSpecificationCharacteristicDTO to save.
     * @param productSpecificationCharacteristicDTO the productSpecificationCharacteristicDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productSpecificationCharacteristicDTO,
     * or with status {@code 400 (Bad Request)} if the productSpecificationCharacteristicDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productSpecificationCharacteristicDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-specification-characteristics/{id}")
    public ResponseEntity<ProductSpecificationCharacteristicDTO> updateProductSpecificationCharacteristic(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody ProductSpecificationCharacteristicDTO productSpecificationCharacteristicDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProductSpecificationCharacteristic : {}, {}", id, productSpecificationCharacteristicDTO);
        if (productSpecificationCharacteristicDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productSpecificationCharacteristicDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productSpecificationCharacteristicRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductSpecificationCharacteristicDTO result = productSpecificationCharacteristicService.update(
            productSpecificationCharacteristicDTO
        );
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productSpecificationCharacteristicDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /product-specification-characteristics/:id} : Partial updates given fields of an existing productSpecificationCharacteristic, field will ignore if it is null
     *
     * @param id the id of the productSpecificationCharacteristicDTO to save.
     * @param productSpecificationCharacteristicDTO the productSpecificationCharacteristicDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productSpecificationCharacteristicDTO,
     * or with status {@code 400 (Bad Request)} if the productSpecificationCharacteristicDTO is not valid,
     * or with status {@code 404 (Not Found)} if the productSpecificationCharacteristicDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the productSpecificationCharacteristicDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/product-specification-characteristics/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProductSpecificationCharacteristicDTO> partialUpdateProductSpecificationCharacteristic(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody ProductSpecificationCharacteristicDTO productSpecificationCharacteristicDTO
    ) throws URISyntaxException {
        log.debug(
            "REST request to partial update ProductSpecificationCharacteristic partially : {}, {}",
            id,
            productSpecificationCharacteristicDTO
        );
        if (productSpecificationCharacteristicDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productSpecificationCharacteristicDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productSpecificationCharacteristicRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductSpecificationCharacteristicDTO> result = productSpecificationCharacteristicService.partialUpdate(
            productSpecificationCharacteristicDTO
        );

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productSpecificationCharacteristicDTO.getId())
        );
    }

    /**
     * {@code GET  /product-specification-characteristics} : get all the productSpecificationCharacteristics.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productSpecificationCharacteristics in body.
     */
    @GetMapping("/product-specification-characteristics")
    public ResponseEntity<List<ProductSpecificationCharacteristicDTO>> getAllProductSpecificationCharacteristics(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of ProductSpecificationCharacteristics");
        Page<ProductSpecificationCharacteristicDTO> page = productSpecificationCharacteristicService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-specification-characteristics/:id} : get the "id" productSpecificationCharacteristic.
     *
     * @param id the id of the productSpecificationCharacteristicDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productSpecificationCharacteristicDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-specification-characteristics/{id}")
    public ResponseEntity<ProductSpecificationCharacteristicDTO> getProductSpecificationCharacteristic(@PathVariable String id) {
        log.debug("REST request to get ProductSpecificationCharacteristic : {}", id);
        Optional<ProductSpecificationCharacteristicDTO> productSpecificationCharacteristicDTO = productSpecificationCharacteristicService.findOne(
            id
        );
        return ResponseUtil.wrapOrNotFound(productSpecificationCharacteristicDTO);
    }

    /**
     * {@code DELETE  /product-specification-characteristics/:id} : delete the "id" productSpecificationCharacteristic.
     *
     * @param id the id of the productSpecificationCharacteristicDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-specification-characteristics/{id}")
    public ResponseEntity<Void> deleteProductSpecificationCharacteristic(@PathVariable String id) {
        log.debug("REST request to delete ProductSpecificationCharacteristic : {}", id);
        productSpecificationCharacteristicService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
