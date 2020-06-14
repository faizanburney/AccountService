package com.fburney.task.model;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.Set;

@Value
@Builder
public class AccountResponse {

    @Singular
    Set<AccountParameter> accounts;
}
