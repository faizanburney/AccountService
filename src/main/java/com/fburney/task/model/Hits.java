package com.fburney.task.model;

import lombok.Data;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.Resource;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class Hits {

    List<Symbols> hits;


    public static class Symbols {
        public String stockName;
        public String stockSymbol;

        public Symbols(String stockName, String stockSymbol) {
            this.stockName = stockName;
            this.stockSymbol = stockSymbol;
        }

        public Symbols() {
        }
    }
    public void fill(String stockName, String stockSymbol)
    {
        if(hits == null)
            hits = new ArrayList<>();
        Symbols securityData = new Symbols();
        securityData.stockName = stockName;
        securityData.stockSymbol = stockSymbol;
        hits.add(securityData);
    }
    public void fill(Resource resource) throws IOException {
        InputStream is = resource.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));


        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(br);

        hits = new ArrayList<>();
        for (CSVRecord record : records) {
            String secSymbol= record.get(0).trim();
            String secName = record.get(1).trim();
            //System.out.println(category);
            Symbols securityData = new Symbols();
            securityData.stockName = secName;
            securityData.stockSymbol = secSymbol;
            hits.add(securityData);
        }
    }
}
