package com.fburney.task.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
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
