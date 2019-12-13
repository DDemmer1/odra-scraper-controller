package de.demmer.dennis.odrascrapercontroller.controller;

import de.demmer.dennis.odrascrapercontroller.entities.Article;
import de.demmer.dennis.odrascrapercontroller.entities.Scraper;
import de.demmer.dennis.odrascrapercontroller.services.ArticleService;
import de.demmer.dennis.odrascrapercontroller.services.ScraperService;
import de.demmer.dennis.odrascrapercontroller.services.scraper.ScraperConnector;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.util.List;

@Log4j2
@RestController
public class MainController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ScraperService scraperService;

    @Autowired
    private ScraperConnector scraperConnector;

    @GetMapping("/articles/{id}")
    public Article getArticleById(@PathVariable int id) throws IOException {
        return articleService.findById(id);
    }

    @GetMapping("/articles/source/{source}")
    public List<Article> getArticleBySourceName(@PathVariable String source,
                                                @RequestParam(value = "limit", required = false) Integer limit,
                                                @RequestParam(value = "query", required = false) String query) throws IOException {
        limit = (limit == null) ? 0 : limit;
        if(query==null||query.equals("")){
            return articleService.findBySourceName(source, limit);
        } else{
            return articleService.findBySourceNameAndQuery(source, limit, query);
        }

    }



    @GetMapping("/scraper/add")
    public String setScraper( @RequestParam(value = "url", required = true) String url) {
        Scraper scraper = new Scraper();
        scraper.setUrl(url);
        scraperService.save(scraper);
        scraperConnector.getArticles();
        return "Scraper was added";
    }



    /*
     *  TODO
     *  "/articles/author/{author}"
     *  "/articles/author/{author}"
     *  "/articles/topic/{topic}"
     */


    @GetMapping("articles/count")
    public long countAll() {
        return articleService.count();
    }

    @GetMapping("/articles/source/{source}/count")
    public long countAllBySourceName(@PathVariable String source) {
        return articleService.countAllBySourceName(source);
    }





}
