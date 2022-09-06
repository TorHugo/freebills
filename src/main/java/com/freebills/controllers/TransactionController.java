package com.freebills.controllers;


import com.freebills.controllers.dtos.requests.TransactionPostRequestDTO;
import com.freebills.controllers.dtos.requests.TransactionPutRequesDTO;
import com.freebills.controllers.dtos.responses.TransactionResponseDTO;
import com.freebills.controllers.mappers.TransactionMapper;
import com.freebills.usecases.CreateTransaction;
import com.freebills.usecases.DeleteTransaction;
import com.freebills.usecases.FindTransaction;
import com.freebills.usecases.UpdateTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/transactions")
public class TransactionController {

    private final TransactionMapper mapper;
    private final CreateTransaction createTransaction;
    private final UpdateTransaction updateTransaction;
    private final FindTransaction findTransaction;
    private final DeleteTransaction deleteTransaction;

    @ResponseStatus(CREATED)
    @PostMapping
    public TransactionResponseDTO save(@RequestBody @Valid final TransactionPostRequestDTO transactionPostRequestDto) {
        final var transaction = mapper.toDomain(transactionPostRequestDto);
        return mapper.fromDomain(createTransaction.create(transaction));
    }

    @ResponseStatus(OK)
    @GetMapping
    public Page<TransactionResponseDTO> byUser(@RequestParam final Long userId, final Pageable pageable) {
        return findTransaction.findAllByUser(userId, pageable).map(mapper::fromDomain);
    }

    @ResponseStatus(OK)
    @GetMapping("/filter")
    public Page<TransactionResponseDTO> byUserDateFilter(@RequestParam final Long userId,
                                                         @RequestParam(required = false) final Integer mounth,
                                                         @RequestParam(required = false) final Integer year,
                                                         @RequestParam(required = false) final String keyword,
                                                         final Pageable pageable) {
        return findTransaction.findAllByUserDateFilter(userId, mounth, year, pageable, keyword).map(mapper::fromDomain);
    }

    @ResponseStatus(OK)
    @PutMapping
    public TransactionResponseDTO update(@RequestBody @Valid final TransactionPutRequesDTO transactionPutRequesDTO) {
        final var transactionFinded = findTransaction.findById(transactionPutRequesDTO.id());
        final var update = updateTransaction.update(mapper.updateTransactionFromDto(transactionPutRequesDTO, transactionFinded));
        return mapper.fromDomain(update);
    }

    @ResponseStatus(OK)
    @GetMapping("/revenue")
    public Page<TransactionResponseDTO> allRevenueByUser(final Pageable pageable, @RequestParam final Long userId) {
        return findTransaction.findAllRevenueByUser(userId, pageable).map(mapper::fromDomain);
    }

    @ResponseStatus(OK)
    @GetMapping("/expense")
    public Page<TransactionResponseDTO> allExpenseByUser(@RequestParam final Long userId, final Pageable pageable) {
        return findTransaction.findAllExpenseByUser(userId, pageable).map(mapper::fromDomain);
    }

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("{id}")
    public void deleteById(@PathVariable final Long id) {
        deleteTransaction.delete(id);
    }
}