package de.demmer.dennis.odrascrapercontroller.controller;

import de.demmer.dennis.odrascrapercontroller.entities.Scraper;
import de.demmer.dennis.odrascrapercontroller.payload.ApiResponse;
import de.demmer.dennis.odrascrapercontroller.services.ScraperService;
import de.demmer.dennis.odrascrapercontroller.services.scraper.ScraperConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DatabaseScraperController {


    @Autowired
    ScraperService scraperService;

    @Autowired
    ScraperConnector scraperConnector;

    @GetMapping("/scraper/add")
    public ApiResponse setScraper(@RequestParam(value = "url", required = true) String url, @RequestParam(value = "name", required = true) String name) {
        try {

            ApiResponse response = scraperService.validate(url);

            if(response.getSuccess()){
                Scraper scraper = new Scraper();
                scraper.setUrl(url);
                scraper.setName(name);
                scraperService.save(scraper);

                Thread thread = new Thread(){
                    public void run(){
                        scraperConnector.callScraper(scraper);
                    }
                };

                thread.start();
            }

            return response;

        } catch (Exception e) {
//            e.printStackTrace();
            return new ApiResponse(false, "Scraper with URL: '" + url + "' was not added. Scraper response validation failed");
        }

    }


    @GetMapping("/scraper/get")
    public List<Scraper> getScraper() {
        return scraperService.getAllScraper();
    }


    @GetMapping("/scraper/delete")
    public ApiResponse deleteScraper(@RequestParam(value = "url", required = true) String url) {
        scraperService.removeScraper(url);
        return new ApiResponse(true, "Scraper with url: " + url + " is deleted");
    }

}
