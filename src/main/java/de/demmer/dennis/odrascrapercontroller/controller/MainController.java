package de.demmer.dennis.odrascrapercontroller.controller;

import de.demmer.dennis.odrascrapercontroller.entities.Article;
import de.demmer.dennis.odrascrapercontroller.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class MainController {

    @Autowired
    ArticleService articleService;

    @GetMapping("/articles/{id}")
    public Article getArticleById(@PathVariable int id) throws IOException {
        return articleService.findById(id);
    }

    @GetMapping("/articles/source/{source}")
    public List<Article> getArticleBySourceName(@PathVariable String source, @RequestParam(value = "limit", required = false) Integer limit) throws IOException {
        return articleService.findBySourceName(source, limit);
    }



    /*
     *  TODO
     *  "/articles/author/{author}"
     *  "/articles/author/{author}"
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
