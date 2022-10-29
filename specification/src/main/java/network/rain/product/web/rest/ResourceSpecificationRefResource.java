package network.rain.product.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
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
 * REST controller for managing {@link network.rain.product.domain.ResourceSpecificationRef}.
 */
@RestController
@RequestMapping("/api")
public class ResourceSpecificationRefResource {

    private final Logger log = LoggerFactory.getLogger(ResourceSpecificationRefResource.class);

    private static final String ENTITY_NAME = "specificationResourceSpecificationRef";

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
    public ResponseEntity<ResourceSpecificationRefDTO> createResourceSpecificationRef(
        @RequestBody ResourceSpecificationRefDTO resourceSpecificationRefDTO
    ) throws URISyntaxException {
        log.debug("REST request to save ResourceSpecificationRef : {}", resourceSpecificationRefDTO);
        if (resourceSpecificationRefDTO.getId() != null) {
            throw new BadRequestAlertException("A new resourceSpecificationRef cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResourceSpecificationRefDTO result = resourceSpecificationRefService.save(resourceSpecificationRefDTO);
        return ResponseEntity
            .created(new URI("/api/resource-specification-refs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
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
    public ResponseEntity<ResourceSpecificationRefDTO> updateResourceSpecificationRef(
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

        if (!resourceSpecificationRefRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ResourceSpecificationRefDTO result = resourceSpecificationRefService.update(resourceSpecificationRefDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, resourceSpecificationRefDTO.getId()))
            .body(result);
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
    public ResponseEntity<ResourceSpecificationRefDTO> partialUpdateResourceSpecificationRef(
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

        if (!resourceSpecificationRefRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ResourceSpecificationRefDTO> result = resourceSpecificationRefService.partialUpdate(resourceSpecificationRefDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, resourceSpecificationRefDTO.getId())
        );
    }

    /**
     * {@code GET  /resource-specification-refs} : get all the resourceSpecificationRefs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of resourceSpecificationRefs in body.
     */
    @GetMapping("/resource-specification-refs")
    public ResponseEntity<List<ResourceSpecificationRefDTO>> getAllResourceSpecificationRefs(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of ResourceSpecificationRefs");
        Page<ResourceSpecificationRefDTO> page = resourceSpecificationRefService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /resource-specification-refs/:id} : get the "id" resourceSpecificationRef.
     *
     * @param id the id of the resourceSpecificationRefDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the resourceSpecificationRefDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/resource-specification-refs/{id}")
    public ResponseEntity<ResourceSpecificationRefDTO> getResourceSpecificationRef(@PathVariable String id) {
        log.debug("REST request to get ResourceSpecificationRef : {}", id);
        Optional<ResourceSpecificationRefDTO> resourceSpecificationRefDTO = resourceSpecificationRefService.findOne(id);
        return ResponseUtil.wrapOrNotFound(resourceSpecificationRefDTO);
    }

    /**
     * {@code DELETE  /resource-specification-refs/:id} : delete the "id" resourceSpecificationRef.
     *
     * @param id the id of the resourceSpecificationRefDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/resource-specification-refs/{id}")
    public ResponseEntity<Void> deleteResourceSpecificationRef(@PathVariable String id) {
        log.debug("REST request to delete ResourceSpecificationRef : {}", id);
        resourceSpecificationRefService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
