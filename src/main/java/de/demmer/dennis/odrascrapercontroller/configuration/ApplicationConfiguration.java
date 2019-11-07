package de.demmer.dennis.odrascrapercontroller.configuration;

import de.demmer.dennis.odrascrapercontroller.services.scraper.ScraperConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


/**
 * Configuration Class
 */
@Component
public class ApplicationConfiguration {


    @EventListener(ApplicationReadyEvent.class)
    public void handleContextRefresh() {
//        TODO on startup do stuff
    }
}
