package network.rain.product.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import network.rain.product.repository.AttachmentRefOrValueRepository;
import network.rain.product.service.AttachmentRefOrValueService;
import network.rain.product.service.dto.AttachmentRefOrValueDTO;
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
 * REST controller for managing {@link network.rain.product.domain.AttachmentRefOrValue}.
 */
@RestController
@RequestMapping("/api")
public class AttachmentRefOrValueResource {

    private final Logger log = LoggerFactory.getLogger(AttachmentRefOrValueResource.class);

    private static final String ENTITY_NAME = "attachmentRefOrValue";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AttachmentRefOrValueService attachmentRefOrValueService;

    private final AttachmentRefOrValueRepository attachmentRefOrValueRepository;

    public AttachmentRefOrValueResource(
        AttachmentRefOrValueService attachmentRefOrValueService,
        AttachmentRefOrValueRepository attachmentRefOrValueRepository
    ) {
        this.attachmentRefOrValueService = attachmentRefOrValueService;
        this.attachmentRefOrValueRepository = attachmentRefOrValueRepository;
    }

    /**
     * {@code POST  /attachment-ref-or-values} : Create a new attachmentRefOrValue.
     *
     * @param attachmentRefOrValueDTO the attachmentRefOrValueDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new attachmentRefOrValueDTO, or with status {@code 400 (Bad Request)} if the attachmentRefOrValue has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/attachment-ref-or-values")
    public Mono<ResponseEntity<AttachmentRefOrValueDTO>> createAttachmentRefOrValue(
        @RequestBody AttachmentRefOrValueDTO attachmentRefOrValueDTO
    ) throws URISyntaxException {
        log.debug("REST request to save AttachmentRefOrValue : {}", attachmentRefOrValueDTO);
        if (attachmentRefOrValueDTO.getId() != null) {
            throw new BadRequestAlertException("A new attachmentRefOrValue cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return attachmentRefOrValueService
            .save(attachmentRefOrValueDTO)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/attachment-ref-or-values/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /attachment-ref-or-values/:id} : Updates an existing attachmentRefOrValue.
     *
     * @param id the id of the attachmentRefOrValueDTO to save.
     * @param attachmentRefOrValueDTO the attachmentRefOrValueDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attachmentRefOrValueDTO,
     * or with status {@code 400 (Bad Request)} if the attachmentRefOrValueDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the attachmentRefOrValueDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/attachment-ref-or-values/{id}")
    public Mono<ResponseEntity<AttachmentRefOrValueDTO>> updateAttachmentRefOrValue(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody AttachmentRefOrValueDTO attachmentRefOrValueDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AttachmentRefOrValue : {}, {}", id, attachmentRefOrValueDTO);
        if (attachmentRefOrValueDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, attachmentRefOrValueDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return attachmentRefOrValueRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return attachmentRefOrValueService
                    .update(attachmentRefOrValueDTO)
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
     * {@code PATCH  /attachment-ref-or-values/:id} : Partial updates given fields of an existing attachmentRefOrValue, field will ignore if it is null
     *
     * @param id the id of the attachmentRefOrValueDTO to save.
     * @param attachmentRefOrValueDTO the attachmentRefOrValueDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attachmentRefOrValueDTO,
     * or with status {@code 400 (Bad Request)} if the attachmentRefOrValueDTO is not valid,
     * or with status {@code 404 (Not Found)} if the attachmentRefOrValueDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the attachmentRefOrValueDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/attachment-ref-or-values/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<AttachmentRefOrValueDTO>> partialUpdateAttachmentRefOrValue(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody AttachmentRefOrValueDTO attachmentRefOrValueDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AttachmentRefOrValue partially : {}, {}", id, attachmentRefOrValueDTO);
        if (attachmentRefOrValueDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, attachmentRefOrValueDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return attachmentRefOrValueRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<AttachmentRefOrValueDTO> result = attachmentRefOrValueService.partialUpdate(attachmentRefOrValueDTO);

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
     * {@code GET  /attachment-ref-or-values} : get all the attachmentRefOrValues.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of attachmentRefOrValues in body.
     */
    @GetMapping("/attachment-ref-or-values")
    public Mono<ResponseEntity<List<AttachmentRefOrValueDTO>>> getAllAttachmentRefOrValues(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        log.debug("REST request to get a page of AttachmentRefOrValues");
        return attachmentRefOrValueService
            .countAll()
            .zipWith(attachmentRefOrValueService.findAll(pageable).collectList())
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
     * {@code GET  /attachment-ref-or-values/:id} : get the "id" attachmentRefOrValue.
     *
     * @param id the id of the attachmentRefOrValueDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the attachmentRefOrValueDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/attachment-ref-or-values/{id}")
    public Mono<ResponseEntity<AttachmentRefOrValueDTO>> getAttachmentRefOrValue(@PathVariable String id) {
        log.debug("REST request to get AttachmentRefOrValue : {}", id);
        Mono<AttachmentRefOrValueDTO> attachmentRefOrValueDTO = attachmentRefOrValueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(attachmentRefOrValueDTO);
    }

    /**
     * {@code DELETE  /attachment-ref-or-values/:id} : delete the "id" attachmentRefOrValue.
     *
     * @param id the id of the attachmentRefOrValueDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/attachment-ref-or-values/{id}")
    public Mono<ResponseEntity<Void>> deleteAttachmentRefOrValue(@PathVariable String id) {
        log.debug("REST request to delete AttachmentRefOrValue : {}", id);
        return attachmentRefOrValueService
            .delete(id)
            .then(
                Mono.just(
                    ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build()
                )
            );
    }
}
