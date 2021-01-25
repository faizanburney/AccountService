package com.fburney.task;

import com.fburney.task.exception.AccountNotFoundException;
import com.fburney.task.rest.AccountController;
import com.fburney.task.service.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.doNothing;


@ExtendWith(SpringExtension.class)
@WebFluxTest(AccountController.class)
@Import(AccountService.class)
public class AccountControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    private AccountService accountService;

    @Test
    public void testGetAccounts() {
        AccountParameter account = AccountParameter.builder()
                .accountId("A123")
                .firstName("testA")
                .lastName("testL")
                .currencyCode("ABC").build();

        Set<AccountParameter> accounts = new HashSet<>();
        accounts.add(account);
        AccountResponse ar = AccountResponse.builder().accounts(accounts).build();
        Flux<AccountResponse> employeeMono = Flux.just(ar);

        Mockito.when(accountService.getAccounts()).thenReturn(employeeMono);

        webTestClient.get()
                .uri("/accounts")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void testGetAccountById() {
        AccountParameter account = AccountParameter.builder()
                .accountId("A123")
                .firstName("testA")
                .lastName("testL")
                .currencyCode("ABC").build();

        Set<AccountParameter> accounts = new HashSet<>();
        accounts.add(account);
        AccountResponse ar = AccountResponse.builder().accounts(accounts).build();
        Flux<AccountResponse> employeeMono = Flux.just(ar);

        Mockito.when(accountService.getAccounts()).thenReturn(employeeMono);

        webTestClient.get()
                .uri("/accounts?id=A123")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void testPostAccounts() {
        AccountParameter account = AccountParameter.builder()
                .accountId("A123")
                .firstName("testA")
                .lastName("testL")
                .currencyCode("ABC").build();

        doNothing().doThrow(new AccountNotFoundException()).when(accountService).saveAccount(account);

        webTestClient.post()
                .uri("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(account), AccountParameter.class)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    public void testPutAccounts() {
        AccountParameter account = AccountParameter.builder()
                .accountId("A123")
                .firstName("testA")
                .lastName("testL")
                .currencyCode("ABCd").build();

        doNothing().doThrow(new AccountNotFoundException()).when(accountService).updateAccount(account);

        webTestClient.put()
                .uri("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(account), AccountParameter.class)
                .exchange()
                .expectStatus().isOk();
    }
/*    @Test
    public void getAccountsList() throws Exception {
        String uri = "/accounts";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        AccountResponse accountResponse = super.mapFromJson(content, AccountResponse.class);
        assertTrue(accountResponse.getAccounts().size() > 0);
    }
    @Test
    public void updateAccount() throws Exception {
        String uri = "/accounts";
        AccountParameter account = AccountParameter.builder()
                .accountId("A123")
                .firstName("testA")
                .lastName("testL")
                .currencyCode("USD").build();

        String inputJson = super.mapToJson(account);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }*/

}
