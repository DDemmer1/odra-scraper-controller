package de.demmer.dennis.odrascrapercontroller.services.scraper;

import de.demmer.dennis.odrascrapercontroller.entities.Article;
import de.demmer.dennis.odrascrapercontroller.entities.Scraper;
import de.demmer.dennis.odrascrapercontroller.services.ArticleService;
import de.demmer.dennis.odrascrapercontroller.services.ScraperService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
    public void callAllScrapers() {
        log.info("Scraping started");

        List<Scraper> scrapers = scraperService.getAllScraper();
        for (Scraper scraper : scrapers) {
            callScraper(scraper);
        }
    }

//    Article[] result1 = restTemplate.getForObject(uri, Article[].class);
//        for (Article article : result1) {
//        if (articleService.save(article)) {
//            newArticles++;
//        }
//    }


    public void callScraper(Scraper scraper){
        String uri = scraper.getUrl();
        try {
            log.info("Connecting with: " + uri);
            int newArticles = 0;
            RestTemplate restTemplate = new RestTemplate();

            Article[] result = restTemplate.getForObject(uri, Article[].class);
            for (Article article : result) {
                if (articleService.save(article)) {
                    newArticles++;
                }
            }


//            ArticleDTO[] result = restTemplate.getForObject(uri, ArticleDTO[].class);
//            loop: for (ArticleDTO article : result) {
//                //TODO workaround fix scrapers with correct name
//                if(article.getSource_name() == null){
//                    Article[] result1 = restTemplate.getForObject(uri, Article[].class);
//                    for (Article article1 : result1) {
//                        if (articleService.save(article1)) {
//                            newArticles++;
//                        }
//                    }
//                    break loop;
//                }
//
//                if (articleService.saveDTO(article)) {
//                    newArticles++;
//                }
//            }

            if (newArticles > 0) {
                log.info("Retrieved " + newArticles + " new article from " + uri);
            }
        } catch (Exception e) {
            log.error("Error while retrieving from " + uri);
            scraper.setError(true);
            scraper.setErrorLog(e.toString());
            scraperService.save(scraper);
            e.printStackTrace();
        }
    }


}
