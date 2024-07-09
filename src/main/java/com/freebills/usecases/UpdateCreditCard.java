package com.freebills.usecases;

import com.freebills.domain.CreditCard;
import com.freebills.gateways.CreditCardGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UpdateCreditCard {

    private final CreditCardGateway creditCardGateway;
    private final VerifyPrincipal verifyPrincipal;

    @Transactional
    public CreditCard execute(final CreditCard creditCard, final String username) {
        verifyPrincipal.execute(creditCard.getAccount().getId(), username);

        return creditCardGateway.update(creditCard);
    }
}
