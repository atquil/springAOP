package com.atquil.springaop.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author atquil
 */

@Data
@Builder
public class PaymentRequestDto {
    private boolean isPaymentValid; //Invalid to through exception
    private boolean isPaymentDone; // To check if method is worked or not
}
