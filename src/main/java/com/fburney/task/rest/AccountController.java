package com.fburney.task.rest;

import com.fburney.task.exception.AccountNotFoundException;
import com.fburney.task.model.Account;
import com.fburney.task.model.AccountParameter;
import com.fburney.task.model.AccountBalance;
import com.fburney.task.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping(path = "accounts")

@Tag(name = "Account", description = "the account API documentation")
public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping
    @Operation(summary = "Get List of all accounts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "found Account", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = AccountParameter.class)))}),
            @ApiResponse(responseCode = "404", description = "No Account found", content = @Content) })
    public ResponseEntity getAccounts()
    {
        return ResponseEntity.ok().body(accountService.getAccounts());
    }


    @Operation(summary = "Get an account by account id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "found the Account", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = AccountParameter.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Account not found", content = @Content) })
    @GetMapping(value = "{id}")
    public ResponseEntity getAccount(
            @Parameter(description = "id of Account to be searched") @PathVariable("id") String id)
    {
        return ResponseEntity.ok().body(accountService.getAccounts());
    }

    @PostMapping(value = "/stock")
    public ResponseEntity saveStocks(@RequestParam String symbol,
                                     @RequestParam String from,
                                     @RequestParam String to,
                                     @RequestParam String name)
    {
        accountService.saveStocks(to,from,symbol,name);
        return ResponseEntity.ok().build();
    }

    @PostMapping (value = "/process")
    public ResponseEntity process() throws IOException {
         accountService.saveSymbolsFromFile();
        return ResponseEntity.ok().build();
    }

    @PostMapping (value = "/update")
    public ResponseEntity update() throws IOException {
        accountService.update();
        return ResponseEntity.ok().build();
    }

    @GetMapping(value="/stocks")
    public ResponseEntity getStocks(@RequestParam String symbol){

        return ResponseEntity.ok().body(accountService.getSecurityDataById(symbol));
    }

    @GetMapping(value="/stocks/indicators")
    public ResponseEntity getStocksIndicators(@RequestParam String symbol){

        return ResponseEntity.ok().body(accountService.getSecurityIndicatorsById(symbol));
    }
    @Operation(summary = "Creates a new Account with zero balance")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Account is created successfully")})
    @PostMapping
    public ResponseEntity saveAccounts(@RequestBody AccountParameter account)
    {
        accountService.saveAccount(account);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Account is created successfully");
    }

    @Operation(summary = "Updates the Account Info but not balance")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated the Account")})
    @PutMapping
    public ResponseEntity updateAccounts(@RequestBody AccountParameter account) {
        accountService.updateAccount(account);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Only updates the balance of an account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "found the Account balance")
    })
    @PutMapping("/balance")
    public ResponseEntity updateAccountBalance(@RequestBody AccountBalance accountBalance) {
        accountService.updateAccountBalance(accountBalance);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get Balance of account in provided currency type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "found the Account", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountBalance.class))})})
    @GetMapping("/{id}/balance")
    public ResponseEntity getAccountBalance(
            @Parameter(description = "id of Account to be searched") @PathVariable("id") String id,
            @RequestParam(defaultValue = "USD") String CurrencyType) throws AccountNotFoundException {
        return ResponseEntity.ok()
                .body(accountService.getBalanceOfAccount(id,CurrencyType));
    }
}
