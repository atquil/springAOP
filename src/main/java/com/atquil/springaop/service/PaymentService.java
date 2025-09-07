package com.atquil.springaop.service;

import com.atquil.springaop.config.annotation.MyCustomAnnotationForPaymentValidation;
import com.atquil.springaop.dto.PaymentRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author atquil
 */

@Service
@Slf4j
public class PaymentService {

    @MyCustomAnnotationForPaymentValidation
    public String processPayment(PaymentRequestDto requestDto) {
        if (!requestDto.isPaymentValid()) {
            throw new IllegalArgumentException("Payment is not valid");
        }
        requestDto.setPaymentDone(true);
        return "Payment processed successfully";
    }
}
