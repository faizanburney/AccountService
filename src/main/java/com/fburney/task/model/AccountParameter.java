package com.fburney.task.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;

@Value
@Builder
public class AccountParameter {


    /**
     * The code that identifies the account.
     *
     * Syntax (PCRE): `/^[a-z][a-z\d]{0,23}$/D`
     */

    @Schema(description = "Unique identifier of the Account",
            example = "Account123", required = true)
    String accountId;

    @Schema(description = "First Name of User",
            example = "John", required = true)
    String firstName;

    @Schema(description = "Last name of user",
            example = "Doe", required = true)
    String lastName;
    /**
     * The ISO 4217 currency code.
     *
     * Syntax (PCRE): `/^[A-Z]{3}$/D` See: https://en.wikipedia.org/wiki/ISO_4217
     */
    @Schema(description = "identifier of the Currency Type(USD,EUR,POUND)",
            example = "USD", required = true)
    String currencyCode;

}
