package de.demmer.dennis.odrascrapercontroller.services;

import de.demmer.dennis.odrascrapercontroller.entities.Scraper;
import de.demmer.dennis.odrascrapercontroller.repositories.ScraperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScraperService {

    @Autowired
    private ScraperRepository scraperRepository;


    public List<String> getAllUrls() {

        List<String> urlList = new ArrayList<>();

        for (Scraper scraper : scraperRepository.findAll()) {
            urlList.add(scraper.getUrl());
        }

        return urlList;
    }


    public void save(Scraper scraper){
        scraperRepository.save(scraper);
    }
}
