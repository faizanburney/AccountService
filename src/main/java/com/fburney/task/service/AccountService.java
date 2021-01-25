package com.fburney.task.service;

import com.fburney.task.SecurityIndicators;
import com.fburney.task.model.Hits;
import com.fburney.task.model.SecurityData;
import com.fburney.task.model.StockResponse;
import com.fburney.task.repo.SecurityRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeriesBuilder;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.num.Num;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AccountService {


    @Autowired
    SecurityRepo securityRepo;


    @Autowired
    StockCommunicator stockCommunicator;

    public void saveStocks(String to, String from, String symbol, String name) {
        StockResponse response = stockCommunicator.getData("https://tvc4.forexpros.com/" +
                "5854b4e968fcb20d935161190761401d/1592657713/1/1/8/" +
                "history", Map.of("symbol", symbol,
                "resolution", "15 ",
                "from", from,
                "to", to), null);
        if (response.s.equals("no_data")) {
            log.error("Data not found for symbol " + symbol);
            return;
        }
        if (response != null) {
            List<SecurityData> securityDataList = new ArrayList<>();
            for (int i = 0; i < response.c.size(); i++) {
                SecurityData securityData1 = new SecurityData();
                securityData1.setCurrentPrice(response.c.get(i));
                securityData1.setHighPrice(response.h.get(i));
                securityData1.setLowPrice(response.l.get(i));
                securityData1.setSymbol(symbol);
                securityData1.setName(name);
                Date time = new Date((long) response.t.get(i) * 1000);

                securityData1.setId(securityData1.getSymbol() + time.toString());
                securityData1.setStartTime(time);
                securityData1.setVolume(response.v.get(i));

                securityDataList.add(securityData1);
            }
            securityRepo.saveAll(securityDataList);
            log.info("saved: " + symbol + " Security:" + name);
        }
    }

    public void saveSymbols() {
        Hits response = stockCommunicator.postData("https://www.investing.com/stock-screener/" +
                "Service/SearchStocks", null, Map.of("X-Requested-With", "XMLHttpRequest", "Cookie",
                "instrumentPageAB=b; adBlockerNewUserDomains=1592657693; __gads=ID=1344a8a328ce901e:T=1592657697:S=ALNI_MYAynx6pMA3EkHHO7gGno27R-Br7A; _ga=GA1.2.1213543104.1592657695; G_ENABLED_IDPS=google; _fbp=fb.1.1592657702448.574697242; usprivacy=1---; notice_behavior=none; instrumentVideoAB90=a; __qca=P0-700735254-1594575411269; OB-USER-TOKEN=a61d7c54-bf54-45d5-ae2d-30b7eb5cea78; PHPSESSID=srg2dhf75qfohokdfkgjcv8k83; geoC=PK; prebid_session=0; StickySession=id.29897133757.360www.investing.com; logglytrackingsession=1d881838-f44e-4133-a198-055fe61b4711; gtmFired=OK; _gid=GA1.2.1226924941.1601230399; SKpbjs-unifiedid=%7B%22TDID%22%3A%2230c71fd4-478e-4cb9-8bbf-e085bb6a0857%22%2C%22TDID_LOOKUP%22%3A%22TRUE%22%2C%22TDID_CREATED_AT%22%3A%222020-08-27T18%3A14%3A45%22%7D; SKpbjs-unifiedid_last=Sun%2C%2027%20Sep%202020%2018%3A14%3A38%20GMT; SKpbjs-id5id=%7B%22ID5ID%22%3A%22ID5-ZHMOnv8lQLmCiit25gFxirm8NwK25LMybGKfbDc02A%22%2C%22ID5ID_CREATED_AT%22%3A%222020-09-18T18%3A03%3A41Z%22%2C%22ID5_CONSENT%22%3Atrue%2C%22CASCADE_NEEDED%22%3Atrue%2C%22ID5ID_LOOKUP%22%3Atrue%2C%223PIDS%22%3A%5B%5D%7D; SKpbjs-id5id_last=Sun%2C%2027%20Sep%202020%2018%3A14%3A38%20GMT; r_p_s_n=1; GED_PLAYLIST_ACTIVITY=W3sidSI6Ik9IYWwiLCJ0c2wiOjE2MDEyMzA1NTUsIm52IjoxLCJ1cHQiOjE2MDEyMzA0NzIsImx0IjoxNjAxMjMwNTUyfV0.; prebid_page=0; _gat=1; OptanonAlertBoxClosed=2020-09-27T18:16:58.421Z; _gat_allSitesTracker=1; nyxDorf=Y2czaGA0ZiQ0Y2BkMmgzLzFhYThhYDIuYWczMQ%3D%3D; OptanonConsent=isIABGlobal=false&datestamp=Sun+Sep+27+2020+23%3A16%3A59+GMT%2B0500+(Pakistan+Standard+Time)&version=6.5.0&hosts=&landingPath=NotLandingPage&groups=C0001%3A1%2CC0002%3A1%2CC0003%3A1%2CC0004%3A1&AwaitingReconsent=false&geolocation=PK%3BSD"));

        for (Hits.Symbols symbols : response.getHits()) {
            this.saveStocks("1604750757", "1561553799", symbols.stockSymbol, symbols.stockName);
        }

    }

    @Autowired
    ResourceLoader resourceLoader;

    public void saveSymbolsFromFile() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:Exchange-Rate.csv");
        Hits obj = new Hits();
        obj.fill(resource);
        for (Hits.Symbols symbols : obj.getHits()) {
            this.saveStocks("1592657859", "1561553799", symbols.stockSymbol, symbols.stockName);
        }
    }

    @Autowired
    MongoTemplate mongoTemplate;

    public void update() throws IOException {
/*        List<String> names = mongoTemplate.getCollection("SecurityData")
                .distinct("name",SecurityData.class);*/
        List<SecurityData> securityData = securityRepo.findDistinctSecurities();
        List<Hits.Symbols> symbolList = securityData.stream().
                map(s -> new Hits.Symbols(s.getName(), s.getSymbol())).collect(Collectors.toList());

        SecurityData last = securityRepo.findFirstByOrderByStartTimeDesc();
        for (Hits.Symbols symbols : symbolList) {
            this.saveStocks(Long.toString(System.currentTimeMillis() / 1000),
                    Long.toString(last.getStartTime().getTime() / 1000), symbols.stockSymbol, symbols.stockName);
        }
    }

    public List<SecurityData> getSecurityDataById(String symbol) {
        return securityRepo.findBySymbolOrderByStartTimeDesc(symbol,
                PageRequest.of(0, 10).next());

    }

    public SecurityIndicators getSecurityIndicatorsById(String symbol) {

        List<SecurityData> securityData = securityRepo.findBySymbolOrderByStartTimeDesc(symbol,
                PageRequest.of(0, 100).next());

        if (!securityData.isEmpty()) {
            BarSeries series = new BaseBarSeriesBuilder().withName(securityData.get(0).getSymbol()).build();
            for (SecurityData sd : securityData) {
                try {

                    series.addBar(ZonedDateTime.of(LocalDateTime.
                                    ofInstant(Instant.ofEpochMilli(sd.getStartTime().getTime()),
                                            ZoneId.systemDefault()),
                            ZoneId.systemDefault()), sd.getCurrentPrice(),
                            sd.getHighPrice(), sd.getLowPrice()
                            , sd.getCurrentPrice(), sd.getVolume());
                } catch (Exception e) {
                    System.out.println("error:" + e);
                }
            }
            Num firstClosePrice = series.getBar(0).getClosePrice();
            ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
// Here is the same close price:
            System.out.println(firstClosePrice.isEqual(closePrice.getValue(0))); // equal to firstClosePrice

// Getting the simple moving average (SMA) of the close price over the last 5 ticks
            SMAIndicator shortSma = new SMAIndicator(closePrice, 5);
// Here is the 5-ticks-SMA value at the 42nd index
            System.out.println("5-ticks-SMA value at the 42nd index: " + shortSma.getValue(42).doubleValue());

// Getting a longer SMA (e.g. over the 30 last ticks)
            SMAIndicator longSma = new SMAIndicator(closePrice, 30);
            return SecurityIndicators.builder().longSMA(1D).shortSMA(1D)
                    .symbol(securityData.get(0).getSymbol()).build();
        }
        return null;
    }
}
