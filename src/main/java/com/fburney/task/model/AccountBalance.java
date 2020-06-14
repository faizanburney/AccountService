package com.fburney.task.model;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AccountBalance {

    /**
     * The code that identifies the account.
     *
     * Syntax (PCRE): `/^[a-z][a-z\d]{0,23}$/D`
     */
    @Schema(description = "Unique identifier of the Account",
            example = "Account123", required = true)
    String accountId;

    /**
     * The value of account balance.
     *
     */
    @Schema(description = "set Account Balance",
            example = "100", required = true)
    Double balance;

    /**
     * The ISO 4217 currency code.
     *
     * Syntax (PCRE): `/^[A-Z]{3}$/D` See: https://en.wikipedia.org/wiki/ISO_4217
     */
    @Schema(description = "identifier of the Currency Type(USD,EUR,POUND)",
            example = "USD", required = true)
    String currencyCode;
}
