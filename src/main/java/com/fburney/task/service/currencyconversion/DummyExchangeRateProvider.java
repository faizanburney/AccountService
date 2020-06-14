package com.fburney.task.service.currencyconversion;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import static javax.management.timer.Timer.ONE_DAY;


/**
 * This class is a placeholder endpoint for Exchange rate provider api.
 * In real world would be coming from any external api
 * just for the task hard coding for few values
 *
 */
@Service
public class DummyExchangeRateProvider implements ExchangeRateProvider {

    // caching the exchange rate and update the cache every day
    //
    @Override
    @Cacheable("exchangeRate")
    public double getExchangeRate(String sourceCurrency, String targetCurrency) {

        try {// to mimic api call
            Thread.sleep(2000);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
        if(sourceCurrency.equals("USD") && targetCurrency.equals("EUR"))
            return 0.89D;
        else if(sourceCurrency.equals("EUR") && targetCurrency.equals("USD"))
            return 1.13D;
        else if(sourceCurrency.equals("USD") && targetCurrency.equals("POUND"))
            return 0.80D;
        else if(sourceCurrency.equals("POUND") && targetCurrency.equals("USD"))
            return 1.25D;
        else
            return 1D;

    }

    @Scheduled(fixedRate = ONE_DAY)
    @CacheEvict(value = { "exchangeRate" })
    public void clearCache()
    {
        // intentionally left blank.
    }
}
