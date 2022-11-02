package com.zerobase.dividend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DividendApplication {

    public static void main(String[] args) {
        SpringApplication.run(DividendApplication.class, args);

        /*
        //YahooFinanceScraper scraper = new YahooFinanceScraper();
        Scraper scraper = new YahooFinanceScraper();
        //var result = scraper.scrap(Company.builder().ticker("coke").build());
        var result = scraper.scrapCompanyByTicker("MMM");
        System.out.println(result);

        */
    }
}
