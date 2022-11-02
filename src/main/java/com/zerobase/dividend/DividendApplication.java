package com.zerobase.dividend;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class DividendApplication {

    public static void main(String[] args) {
        //SpringApplication.run(DividendApplication.class, args);

        try {
            Connection connection = Jsoup.connect("https://finance.yahoo.com/quote/COKE/history?period1=99100800&period2=1667260800&interval=1mo&filter=history&frequency=1mo&includeAdjustedClose=true");
            Document document = connection.get();

            Elements elements = document.getElementsByAttributeValue("data-test", "historical-prices");
            Element element = elements.get(0);

            Element tbody = element.children().get(1);
            for(Element e : tbody.children()) {
                String text = e.text();
                if (!text.endsWith("Dividend")) {
                    continue;
                }

                String[] splits = text.split(" ");
                String month = splits[0];
                int day = Integer.parseInt(splits[1].replace(",", ""));
                int year = Integer.parseInt(splits[2]);
                String dividend = splits[3];

                System.out.println(year + "/" + month + "/" + day + " -> " + dividend);

                /* 출력값
                *   2022/Oct/26 -> 0.25
                    2022/Jul/28 -> 0.25
                    2022/Apr/27 -> 0.25
                    2022/Jan/27 -> 0.25
                    2021/Oct/21 -> 0.25
                    2021/Jul/22 -> 0.25
                    2021/Apr/22 -> 0.25
                    2021/Jan/21 -> 0.25
                    2020/Oct/22 -> 0.25
                    2020/Jul/23 -> 0.25
                    2020/Apr/22 -> 0.25
                    2020/Jan/23 -> 0.25
                    2019/Oct/24 -> 0.25
                    2019/Jul/25 -> 0.25
                    2019/Apr/25 -> 0.25
                    2019/Jan/24 -> 0.25
                    2018/Oct/25 -> 0.25
                    2018/Jul/26 -> 0.25
                    2018/Apr/26 -> 0.25
                    2018/Jan/25 -> 0.25
                    2017/Oct/26 -> 0.25
                    2017/Jul/26 -> 0.25
                    2017/Apr/26 -> 0.25
                    2017/Jan/25 -> 0.25
                    2016/Oct/26 -> 0.25

                * */
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
