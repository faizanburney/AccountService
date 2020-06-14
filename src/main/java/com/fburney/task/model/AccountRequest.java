package com.fburney.task.model;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class AccountRequest {
    AccountParameter accountParameter;
}
