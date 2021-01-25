package com.fburney.task;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SecurityIndicators {
    String symbol;
    Double shortSMA;
    Double longSMA;
}
