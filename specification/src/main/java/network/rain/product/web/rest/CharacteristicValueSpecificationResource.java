package network.rain.product.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import network.rain.product.repository.CharacteristicValueSpecificationRepository;
import network.rain.product.service.CharacteristicValueSpecificationService;
import network.rain.product.service.dto.CharacteristicValueSpecificationDTO;
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
 * REST controller for managing {@link network.rain.product.domain.CharacteristicValueSpecification}.
 */
@RestController
@RequestMapping("/api")
public class CharacteristicValueSpecificationResource {

    private final Logger log = LoggerFactory.getLogger(CharacteristicValueSpecificationResource.class);

    private static final String ENTITY_NAME = "specificationCharacteristicValueSpecification";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CharacteristicValueSpecificationService characteristicValueSpecificationService;

    private final CharacteristicValueSpecificationRepository characteristicValueSpecificationRepository;

    public CharacteristicValueSpecificationResource(
        CharacteristicValueSpecificationService characteristicValueSpecificationService,
        CharacteristicValueSpecificationRepository characteristicValueSpecificationRepository
    ) {
        this.characteristicValueSpecificationService = characteristicValueSpecificationService;
        this.characteristicValueSpecificationRepository = characteristicValueSpecificationRepository;
    }

    /**
     * {@code POST  /characteristic-value-specifications} : Create a new characteristicValueSpecification.
     *
     * @param characteristicValueSpecificationDTO the characteristicValueSpecificationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new characteristicValueSpecificationDTO, or with status {@code 400 (Bad Request)} if the characteristicValueSpecification has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/characteristic-value-specifications")
    public ResponseEntity<CharacteristicValueSpecificationDTO> createCharacteristicValueSpecification(
        @RequestBody CharacteristicValueSpecificationDTO characteristicValueSpecificationDTO
    ) throws URISyntaxException {
        log.debug("REST request to save CharacteristicValueSpecification : {}", characteristicValueSpecificationDTO);
        if (characteristicValueSpecificationDTO.getId() != null) {
            throw new BadRequestAlertException("A new characteristicValueSpecification cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CharacteristicValueSpecificationDTO result = characteristicValueSpecificationService.save(characteristicValueSpecificationDTO);
        return ResponseEntity
            .created(new URI("/api/characteristic-value-specifications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /characteristic-value-specifications/:id} : Updates an existing characteristicValueSpecification.
     *
     * @param id the id of the characteristicValueSpecificationDTO to save.
     * @param characteristicValueSpecificationDTO the characteristicValueSpecificationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated characteristicValueSpecificationDTO,
     * or with status {@code 400 (Bad Request)} if the characteristicValueSpecificationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the characteristicValueSpecificationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/characteristic-value-specifications/{id}")
    public ResponseEntity<CharacteristicValueSpecificationDTO> updateCharacteristicValueSpecification(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CharacteristicValueSpecificationDTO characteristicValueSpecificationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CharacteristicValueSpecification : {}, {}", id, characteristicValueSpecificationDTO);
        if (characteristicValueSpecificationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, characteristicValueSpecificationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!characteristicValueSpecificationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CharacteristicValueSpecificationDTO result = characteristicValueSpecificationService.update(characteristicValueSpecificationDTO);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    characteristicValueSpecificationDTO.getId().toString()
                )
            )
            .body(result);
    }

    /**
     * {@code PATCH  /characteristic-value-specifications/:id} : Partial updates given fields of an existing characteristicValueSpecification, field will ignore if it is null
     *
     * @param id the id of the characteristicValueSpecificationDTO to save.
     * @param characteristicValueSpecificationDTO the characteristicValueSpecificationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated characteristicValueSpecificationDTO,
     * or with status {@code 400 (Bad Request)} if the characteristicValueSpecificationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the characteristicValueSpecificationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the characteristicValueSpecificationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/characteristic-value-specifications/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CharacteristicValueSpecificationDTO> partialUpdateCharacteristicValueSpecification(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CharacteristicValueSpecificationDTO characteristicValueSpecificationDTO
    ) throws URISyntaxException {
        log.debug(
            "REST request to partial update CharacteristicValueSpecification partially : {}, {}",
            id,
            characteristicValueSpecificationDTO
        );
        if (characteristicValueSpecificationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, characteristicValueSpecificationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!characteristicValueSpecificationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CharacteristicValueSpecificationDTO> result = characteristicValueSpecificationService.partialUpdate(
            characteristicValueSpecificationDTO
        );

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, characteristicValueSpecificationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /characteristic-value-specifications} : get all the characteristicValueSpecifications.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of characteristicValueSpecifications in body.
     */
    @GetMapping("/characteristic-value-specifications")
    public ResponseEntity<List<CharacteristicValueSpecificationDTO>> getAllCharacteristicValueSpecifications(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of CharacteristicValueSpecifications");
        Page<CharacteristicValueSpecificationDTO> page = characteristicValueSpecificationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /characteristic-value-specifications/:id} : get the "id" characteristicValueSpecification.
     *
     * @param id the id of the characteristicValueSpecificationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the characteristicValueSpecificationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/characteristic-value-specifications/{id}")
    public ResponseEntity<CharacteristicValueSpecificationDTO> getCharacteristicValueSpecification(@PathVariable Long id) {
        log.debug("REST request to get CharacteristicValueSpecification : {}", id);
        Optional<CharacteristicValueSpecificationDTO> characteristicValueSpecificationDTO = characteristicValueSpecificationService.findOne(
            id
        );
        return ResponseUtil.wrapOrNotFound(characteristicValueSpecificationDTO);
    }

    /**
     * {@code DELETE  /characteristic-value-specifications/:id} : delete the "id" characteristicValueSpecification.
     *
     * @param id the id of the characteristicValueSpecificationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/characteristic-value-specifications/{id}")
    public ResponseEntity<Void> deleteCharacteristicValueSpecification(@PathVariable Long id) {
        log.debug("REST request to delete CharacteristicValueSpecification : {}", id);
        characteristicValueSpecificationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
