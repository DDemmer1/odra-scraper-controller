package de.demmer.dennis.odrascrapercontroller.configuration;

import de.demmer.dennis.odrascrapercontroller.entities.Scraper;
import de.demmer.dennis.odrascrapercontroller.repositories.ScraperRepository;
import de.demmer.dennis.odrascrapercontroller.services.ScraperService;
import de.demmer.dennis.odrascrapercontroller.services.TwitterService;
import de.demmer.dennis.odrascrapercontroller.services.scraper.ScraperConnector;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * Configuration Class
 */
@Log4j2
@Component
public class ApplicationConfiguration {

    @Autowired
    ScraperRepository scraperRepository;

    @Autowired
    ScraperService scraperService;

    @Autowired
    ScraperConnector scraperConnector;

    @Autowired
    TwitterService twitterService;

    @Value("${scraper.urls}")
    List<String> defaultScraper;

    @Value("${scraper.names}")
    List<String> names;


    @EventListener(ApplicationReadyEvent.class)
    public void handleContextRefresh() {

        int index = 0;
        if (defaultScraper != null && defaultScraper.size() > 0){
            log.info("Set default scrapers");
            log.info("------------------------------------------------------------------------");
            for (String url : defaultScraper) {
                if (scraperRepository.findByUrl(url) == null) {
                    Scraper scraper = new Scraper();
                    scraper.setUrl(url);
                    scraper.setName(names.get(index));
                    log.info("Scraper no. " + index);
                    log.info("URL: " + url);
                    log.info("Name: " + names.get(index));
                    scraperService.save(scraper);
                    scraperConnector.callScraper(scraper);
                    log.info("------------------------------------------------------------------------");

                }
                index++;
            }
        }

        //Open Twitter Tracks
        twitterService.openAllTracks();



    }
}
