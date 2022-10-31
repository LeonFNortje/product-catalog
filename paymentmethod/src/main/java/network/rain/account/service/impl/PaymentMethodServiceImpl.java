package network.rain.account.service.impl;

import java.util.Optional;
import network.rain.account.domain.PaymentMethod;
import network.rain.account.repository.PaymentMethodRepository;
import network.rain.account.service.PaymentMethodService;
import network.rain.account.service.dto.PaymentMethodDTO;
import network.rain.account.service.mapper.PaymentMethodMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public PaymentMethodDTO save(PaymentMethodDTO paymentMethodDTO) {
        log.debug("Request to save PaymentMethod : {}", paymentMethodDTO);
        PaymentMethod paymentMethod = paymentMethodMapper.toEntity(paymentMethodDTO);
        paymentMethod = paymentMethodRepository.save(paymentMethod);
        return paymentMethodMapper.toDto(paymentMethod);
    }

    @Override
    public PaymentMethodDTO update(PaymentMethodDTO paymentMethodDTO) {
        log.debug("Request to update PaymentMethod : {}", paymentMethodDTO);
        PaymentMethod paymentMethod = paymentMethodMapper.toEntity(paymentMethodDTO);
        paymentMethod.setIsPersisted();
        paymentMethod = paymentMethodRepository.save(paymentMethod);
        return paymentMethodMapper.toDto(paymentMethod);
    }

    @Override
    public Optional<PaymentMethodDTO> partialUpdate(PaymentMethodDTO paymentMethodDTO) {
        log.debug("Request to partially update PaymentMethod : {}", paymentMethodDTO);

        return paymentMethodRepository
            .findById(paymentMethodDTO.getId())
            .map(existingPaymentMethod -> {
                paymentMethodMapper.partialUpdate(existingPaymentMethod, paymentMethodDTO);

                return existingPaymentMethod;
            })
            .map(paymentMethodRepository::save)
            .map(paymentMethodMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaymentMethodDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PaymentMethods");
        return paymentMethodRepository.findAll(pageable).map(paymentMethodMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PaymentMethodDTO> findOne(String id) {
        log.debug("Request to get PaymentMethod : {}", id);
        return paymentMethodRepository.findById(id).map(paymentMethodMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete PaymentMethod : {}", id);
        paymentMethodRepository.deleteById(id);
    }
}
