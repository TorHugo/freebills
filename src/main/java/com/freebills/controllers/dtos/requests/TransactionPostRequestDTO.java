package com.freebills.controllers.dtos.requests;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public record TransactionPostRequestDTO(

        @NotNull
        Long accountId,

        @NotNull
        Double amount,

        boolean paid,

        @NotBlank
        String description,

        @NotNull
        LocalDate date,

        @NotBlank
        String transactionType,

        @NotBlank
        String transactionCategory,

        String barCode
) {
}
