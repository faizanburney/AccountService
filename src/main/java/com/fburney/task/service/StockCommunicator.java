package com.fburney.task.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fburney.task.model.Hits;
import com.fburney.task.model.StockResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class StockCommunicator {

    @Autowired
    RestTemplate restTemplate;


    public StockResponse getData(String url, Map<String, String> parameters, Map<String, String> header) {
        StockResponse responseString = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
//            httpHeaders.set("Content-Type", "application/json");
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.set("lang", LocaleContextHolder.getLocale().getLanguage());
            httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            if (header != null) {
                for (String key : header.keySet()) {
                    httpHeaders.set(key, header.get(key));
                }
            }

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
            if (parameters != null) {
                for (Map.Entry<String, String> pair : parameters.entrySet()) {
                    if (pair.getValue() != null)
                        builder.queryParam(pair.getKey(), URLEncoder.encode(pair.getValue(), "UTF-8"));
//                    builder.queryParam(pair.getKey(), pair.getValue());
                }
            }

            URI urlObject = builder.build(true).toUri();

            HttpEntity requestEntity = new HttpEntity(null, httpHeaders);
            System.out.println(" ---- Http Request sent ---- ");
            System.out.println("Http Request url --> " + urlObject.toString());


            ResponseEntity<StockResponse> responseEntity = restTemplate.exchange(urlObject, HttpMethod.GET,
                    requestEntity, StockResponse.class);


            if (responseEntity == null || responseEntity.getBody() == null) {
                log.error("Empty response from service.");
            }

            responseString = responseEntity.getBody();

            log.info(" ---- Http response received ---- ");
            log.info("Http Request url --> " + url);
            log.info("Http Response received --> " + responseString);

        } catch (IOException ex) {
            log.error("An exception has occurred: " + ex.getMessage());
        } catch (HttpClientErrorException ex) {
            log.error("An exception has occurred: " + ex.getMessage());
            log.error(" ---- Http response received ---- ");
            log.error("Http Request url --> " + url);
            log.error("Http Response Code --> " + ex.getStatusCode().value());
            log.error("Http Response received --> " + ex.getResponseBodyAsString());

            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                responseString = null;
            }

        } catch (ResourceAccessException ex) {
            System.out.println("An exception has occurred: " + ex.getMessage());
        }
        return responseString;
    }

    public Hits postData(String url, Map<String, String> parameters, Map<String, String> header) {
        Hits responseString = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
//            httpHeaders.set("Content-Type", "application/json");
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.set("lang", LocaleContextHolder.getLocale().getLanguage());
            httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            if (header != null) {
                for (String key : header.keySet()) {
                    httpHeaders.set(key, header.get(key));
                }
            }

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
            if (parameters != null) {
                for (Map.Entry<String, String> pair : parameters.entrySet()) {
                    if (pair.getValue() != null)
                        builder.queryParam(pair.getKey(), URLEncoder.encode(pair.getValue(), "UTF-8"));
//                    builder.queryParam(pair.getKey(), pair.getValue());
                }
            }

            URI urlObject = builder.build(true).toUri();

            HttpEntity requestEntity = new HttpEntity(null, httpHeaders);
            System.out.println(" ---- Http Request sent ---- ");
            System.out.println("Http Request url --> " + urlObject.toString());


            ResponseEntity<Hits> responseEntity = restTemplate.exchange(urlObject, HttpMethod.POST,
                    requestEntity, Hits.class);


            if (responseEntity == null || responseEntity.getBody() == null) {
                log.error("Empty response from service.");
            }

            responseString = responseEntity.getBody();

            log.info(" ---- Http response received ---- ");
            log.info("Http Request url --> " + url);
            log.info("Http Response received --> " + responseString);

        } catch (IOException ex) {
            log.error("An exception has occurred: " + ex.getMessage());
        } catch (HttpClientErrorException ex) {
            log.error("An exception has occurred: " + ex.getMessage());
            log.error(" ---- Http response received ---- ");
            log.error("Http Request url --> " + url);
            log.error("Http Response Code --> " + ex.getStatusCode().value());
            log.error("Http Response received --> " + ex.getResponseBodyAsString());

            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                responseString = null;
            }

        } catch (ResourceAccessException ex) {
            System.out.println("An exception has occurred: " + ex.getMessage());
        }
        return responseString;
    }
}
