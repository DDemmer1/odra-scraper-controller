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
import java.util.ArrayList;
import java.util.List;

@Log4j2
@RestController
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/articles/{id}")
    public Article getArticleById(@PathVariable int id) throws IOException {
        return articleService.findById(id);
    }

    @GetMapping("/articles/source/{source}")
    public List<Article> getArticleBySourceName(@PathVariable String source,
                                                @RequestParam(value = "limit", required = false) Integer limit,
                                                @RequestParam(value = "query", required = false) String query) throws IOException {
        if(source.equals("test")){
            return articleService.test();
        }


        limit = (limit == null) ? 0 : limit;
        if(query==null||query.equals("")){
            return articleService.findBySourceName(source, limit);
        } else{
            return articleService.findBySourceNameAndQuery(source, limit, query);
        }

    }

    @GetMapping("articles/count")
    public long countAll() {
        return articleService.count();
    }

    @GetMapping("/articles/source/{source}/count")
    public long countAllBySourceName(@PathVariable String source) {
        return articleService.countAllBySourceName(source);
    }





}
