package com.fburney.task.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
public class Account {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private Double currentBalance;
    private String currencyType;
    private Date joinDate;

}
