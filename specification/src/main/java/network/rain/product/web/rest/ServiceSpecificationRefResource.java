package network.rain.product.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
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
 * REST controller for managing {@link network.rain.product.domain.ServiceSpecificationRef}.
 */
@RestController
@RequestMapping("/api")
public class ServiceSpecificationRefResource {

    private final Logger log = LoggerFactory.getLogger(ServiceSpecificationRefResource.class);

    private static final String ENTITY_NAME = "specificationServiceSpecificationRef";

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
    public ResponseEntity<ServiceSpecificationRefDTO> createServiceSpecificationRef(
        @RequestBody ServiceSpecificationRefDTO serviceSpecificationRefDTO
    ) throws URISyntaxException {
        log.debug("REST request to save ServiceSpecificationRef : {}", serviceSpecificationRefDTO);
        if (serviceSpecificationRefDTO.getId() != null) {
            throw new BadRequestAlertException("A new serviceSpecificationRef cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceSpecificationRefDTO result = serviceSpecificationRefService.save(serviceSpecificationRefDTO);
        return ResponseEntity
            .created(new URI("/api/service-specification-refs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
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
    public ResponseEntity<ServiceSpecificationRefDTO> updateServiceSpecificationRef(
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

        if (!serviceSpecificationRefRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ServiceSpecificationRefDTO result = serviceSpecificationRefService.update(serviceSpecificationRefDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceSpecificationRefDTO.getId()))
            .body(result);
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
    public ResponseEntity<ServiceSpecificationRefDTO> partialUpdateServiceSpecificationRef(
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

        if (!serviceSpecificationRefRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ServiceSpecificationRefDTO> result = serviceSpecificationRefService.partialUpdate(serviceSpecificationRefDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceSpecificationRefDTO.getId())
        );
    }

    /**
     * {@code GET  /service-specification-refs} : get all the serviceSpecificationRefs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceSpecificationRefs in body.
     */
    @GetMapping("/service-specification-refs")
    public ResponseEntity<List<ServiceSpecificationRefDTO>> getAllServiceSpecificationRefs(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of ServiceSpecificationRefs");
        Page<ServiceSpecificationRefDTO> page = serviceSpecificationRefService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /service-specification-refs/:id} : get the "id" serviceSpecificationRef.
     *
     * @param id the id of the serviceSpecificationRefDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceSpecificationRefDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/service-specification-refs/{id}")
    public ResponseEntity<ServiceSpecificationRefDTO> getServiceSpecificationRef(@PathVariable String id) {
        log.debug("REST request to get ServiceSpecificationRef : {}", id);
        Optional<ServiceSpecificationRefDTO> serviceSpecificationRefDTO = serviceSpecificationRefService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceSpecificationRefDTO);
    }

    /**
     * {@code DELETE  /service-specification-refs/:id} : delete the "id" serviceSpecificationRef.
     *
     * @param id the id of the serviceSpecificationRefDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/service-specification-refs/{id}")
    public ResponseEntity<Void> deleteServiceSpecificationRef(@PathVariable String id) {
        log.debug("REST request to delete ServiceSpecificationRef : {}", id);
        serviceSpecificationRefService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
