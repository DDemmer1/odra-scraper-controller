package de.demmer.dennis.odrascrapercontroller.services;

import de.demmer.dennis.odrascrapercontroller.entities.Scraper;
import de.demmer.dennis.odrascrapercontroller.payload.ApiResponse;
import de.demmer.dennis.odrascrapercontroller.repositories.ScraperRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Transactional
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


    public List<String> getAllScraperNames() {

        List<String> scraperNames = new ArrayList<>();

        for (Scraper scraper : scraperRepository.findAll()) {
            scraperNames.add(scraper.getName());
        }

        return scraperNames;
    }


    public void save(Scraper scraper) {
        scraperRepository.save(scraper);
    }

    public List<Scraper> getAllScraper() {
        List<Scraper> scrapers = new ArrayList<>();
        for (Scraper scraper : scraperRepository.findAll()) {
            scrapers.add(scraper);
        }
        return scrapers;
    }

    public ApiResponse validate(String urlStr) throws IOException {

        URL url = new URL(urlStr);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();

        String siteContent = content.toString();
        JSONArray response = new JSONArray(siteContent);

        //begin validation

        JSONObject firstArticle = response.getJSONObject(0);

        String[] fields = {"headline", "textBody", "link", "source" , "sourceName"};

        for (String field: fields) {
            ApiResponse apiResponse = validateJSONField(field,firstArticle);
            if(!apiResponse.getSuccess()){
                return apiResponse;
            }
        }
        return new ApiResponse(true,"Scraper with URL: '" + url + "' was validated and added.");
    }


    private ApiResponse validateJSONField(String key, JSONObject object){
        String string = object.getString(key);

        if(string == null || string.equals("")){
            return new ApiResponse(false, "No or empty '"+ key +"'. Scraper was not added.");
        }
        return new ApiResponse(true, "");

    }

    public void removeScraper(String url){
        scraperRepository.deleteByUrl(url);
    }
}
