package com.atquil.springaop.controller;

import com.atquil.springaop.dto.PaymentRequestDto;
import com.atquil.springaop.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author atquil
 */
@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/process")
    @Operation(summary = "Process payment with validation", description = "Triggers AOP advice using custom annotation")
    public String processPayment(@RequestBody PaymentRequestDto dto) {
        return paymentService.processPayment(dto);
    }
}