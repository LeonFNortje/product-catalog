package network.rain.product.service.impl;

import network.rain.product.domain.PaymentMethod;
import network.rain.product.repository.PaymentMethodRepository;
import network.rain.product.service.PaymentMethodService;
import network.rain.product.service.dto.PaymentMethodDTO;
import network.rain.product.service.mapper.PaymentMethodMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link PaymentMethod}.
 */
@Service
@Transactional
public class PaymentMethodServiceImpl implements PaymentMethodService {

    private final Logger log = LoggerFactory.getLogger(PaymentMethodServiceImpl.class);

    private final PaymentMethodRepository paymentMethodRepository;

    private final PaymentMethodMapper paymentMethodMapper;

    public PaymentMethodServiceImpl(PaymentMethodRepository paymentMethodRepository, PaymentMethodMapper paymentMethodMapper) {
        this.paymentMethodRepository = paymentMethodRepository;
        this.paymentMethodMapper = paymentMethodMapper;
    }

    @Override
    public Mono<PaymentMethodDTO> save(PaymentMethodDTO paymentMethodDTO) {
        log.debug("Request to save PaymentMethod : {}", paymentMethodDTO);
        return paymentMethodRepository.save(paymentMethodMapper.toEntity(paymentMethodDTO)).map(paymentMethodMapper::toDto);
    }

    @Override
    public Mono<PaymentMethodDTO> update(PaymentMethodDTO paymentMethodDTO) {
        log.debug("Request to update PaymentMethod : {}", paymentMethodDTO);
        return paymentMethodRepository
            .save(paymentMethodMapper.toEntity(paymentMethodDTO).setIsPersisted())
            .map(paymentMethodMapper::toDto);
    }

    @Override
    public Mono<PaymentMethodDTO> partialUpdate(PaymentMethodDTO paymentMethodDTO) {
        log.debug("Request to partially update PaymentMethod : {}", paymentMethodDTO);

        return paymentMethodRepository
            .findById(paymentMethodDTO.getId())
            .map(existingPaymentMethod -> {
                paymentMethodMapper.partialUpdate(existingPaymentMethod, paymentMethodDTO);

                return existingPaymentMethod;
            })
            .flatMap(paymentMethodRepository::save)
            .map(paymentMethodMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<PaymentMethodDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PaymentMethods");
        return paymentMethodRepository.findAllBy(pageable).map(paymentMethodMapper::toDto);
    }

    public Mono<Long> countAll() {
        return paymentMethodRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<PaymentMethodDTO> findOne(String id) {
        log.debug("Request to get PaymentMethod : {}", id);
        return paymentMethodRepository.findById(id).map(paymentMethodMapper::toDto);
    }

    @Override
    public Mono<Void> delete(String id) {
        log.debug("Request to delete PaymentMethod : {}", id);
        return paymentMethodRepository.deleteById(id);
    }
}
