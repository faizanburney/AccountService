package com.fburney.task.rest;

import com.fburney.task.service.AccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(path = "accounts")

@Tag(name = "Account", description = "the account API documentation")
public class AccountController {

    @Autowired
    AccountService accountService;


    @PostMapping(value = "/stock")
    public ResponseEntity saveStocks(@RequestParam String symbol,
                                     @RequestParam String from,
                                     @RequestParam String to,
                                     @RequestParam String name) {
        accountService.saveStocks(to, from, symbol, name);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/process")
    public ResponseEntity process() throws IOException {
        accountService.saveSymbolsFromFile();
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/update")
    public ResponseEntity update() throws IOException {
        accountService.update();
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/stocks")
    public ResponseEntity getStocks(@RequestParam String symbol) {

        return ResponseEntity.ok().body(accountService.getSecurityDataById(symbol));
    }

    @GetMapping(value = "/stocks/indicators")
    public ResponseEntity getStocksIndicators(@RequestParam String symbol) {

        return ResponseEntity.ok().body(accountService.getSecurityIndicatorsById(symbol));
    }

}
