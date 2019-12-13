package de.demmer.dennis.odrascrapercontroller.services.scraper;

import de.demmer.dennis.odrascrapercontroller.entities.Article;
import de.demmer.dennis.odrascrapercontroller.services.ArticleService;
import de.demmer.dennis.odrascrapercontroller.services.ScraperService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@EnableAsync
@Service
public class ScraperConnector {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ScraperService scraperService;


    @Async
    @Scheduled(fixedRateString = "${scraper.interval}")
    public void getArticles() {

        List<String> links = scraperService.getAllUrls();
        for (String uri : links) {
            try {
                System.out.println(uri);
                int newArticles = 0;
                RestTemplate restTemplate = new RestTemplate();
                Article[] result = restTemplate.getForObject(uri, Article[].class);
                for (Article article : result) {
                    if (articleService.save(article)) {
                        newArticles++;
                    }
                }
                if (newArticles > 0) {
                    log.info("Retrieved " + newArticles + " new article from " + uri);
                }
            } catch (Exception e) {
                log.error("Error while retrieving from " + uri);
                e.printStackTrace();
            }

        }
    }

}
