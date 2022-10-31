package network.rain.account.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import network.rain.account.repository.RelatedPlaceRepository;
import network.rain.account.service.RelatedPlaceService;
import network.rain.account.service.dto.RelatedPlaceDTO;
import network.rain.account.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link network.rain.account.domain.RelatedPlace}.
 */
@RestController
@RequestMapping("/api")
public class RelatedPlaceResource {

    private final Logger log = LoggerFactory.getLogger(RelatedPlaceResource.class);

    private static final String ENTITY_NAME = "paymentmethodRelatedPlace";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RelatedPlaceService relatedPlaceService;

    private final RelatedPlaceRepository relatedPlaceRepository;

    public RelatedPlaceResource(RelatedPlaceService relatedPlaceService, RelatedPlaceRepository relatedPlaceRepository) {
        this.relatedPlaceService = relatedPlaceService;
        this.relatedPlaceRepository = relatedPlaceRepository;
    }

    /**
     * {@code POST  /related-places} : Create a new relatedPlace.
     *
     * @param relatedPlaceDTO the relatedPlaceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new relatedPlaceDTO, or with status {@code 400 (Bad Request)} if the relatedPlace has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/related-places")
    public ResponseEntity<RelatedPlaceDTO> createRelatedPlace(@RequestBody RelatedPlaceDTO relatedPlaceDTO) throws URISyntaxException {
        log.debug("REST request to save RelatedPlace : {}", relatedPlaceDTO);
        if (relatedPlaceDTO.getId() != null) {
            throw new BadRequestAlertException("A new relatedPlace cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RelatedPlaceDTO result = relatedPlaceService.save(relatedPlaceDTO);
        return ResponseEntity
            .created(new URI("/api/related-places/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /related-places/:id} : Updates an existing relatedPlace.
     *
     * @param id the id of the relatedPlaceDTO to save.
     * @param relatedPlaceDTO the relatedPlaceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated relatedPlaceDTO,
     * or with status {@code 400 (Bad Request)} if the relatedPlaceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the relatedPlaceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/related-places/{id}")
    public ResponseEntity<RelatedPlaceDTO> updateRelatedPlace(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody RelatedPlaceDTO relatedPlaceDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RelatedPlace : {}, {}", id, relatedPlaceDTO);
        if (relatedPlaceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, relatedPlaceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!relatedPlaceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RelatedPlaceDTO result = relatedPlaceService.update(relatedPlaceDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, relatedPlaceDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /related-places/:id} : Partial updates given fields of an existing relatedPlace, field will ignore if it is null
     *
     * @param id the id of the relatedPlaceDTO to save.
     * @param relatedPlaceDTO the relatedPlaceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated relatedPlaceDTO,
     * or with status {@code 400 (Bad Request)} if the relatedPlaceDTO is not valid,
     * or with status {@code 404 (Not Found)} if the relatedPlaceDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the relatedPlaceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/related-places/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RelatedPlaceDTO> partialUpdateRelatedPlace(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody RelatedPlaceDTO relatedPlaceDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RelatedPlace partially : {}, {}", id, relatedPlaceDTO);
        if (relatedPlaceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, relatedPlaceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!relatedPlaceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RelatedPlaceDTO> result = relatedPlaceService.partialUpdate(relatedPlaceDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, relatedPlaceDTO.getId())
        );
    }

    /**
     * {@code GET  /related-places} : get all the relatedPlaces.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of relatedPlaces in body.
     */
    @GetMapping("/related-places")
    public ResponseEntity<List<RelatedPlaceDTO>> getAllRelatedPlaces(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of RelatedPlaces");
        Page<RelatedPlaceDTO> page = relatedPlaceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /related-places/:id} : get the "id" relatedPlace.
     *
     * @param id the id of the relatedPlaceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the relatedPlaceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/related-places/{id}")
    public ResponseEntity<RelatedPlaceDTO> getRelatedPlace(@PathVariable String id) {
        log.debug("REST request to get RelatedPlace : {}", id);
        Optional<RelatedPlaceDTO> relatedPlaceDTO = relatedPlaceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(relatedPlaceDTO);
    }

    /**
     * {@code DELETE  /related-places/:id} : delete the "id" relatedPlace.
     *
     * @param id the id of the relatedPlaceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/related-places/{id}")
    public ResponseEntity<Void> deleteRelatedPlace(@PathVariable String id) {
        log.debug("REST request to delete RelatedPlace : {}", id);
        relatedPlaceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
