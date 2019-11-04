package de.demmer.dennis.odrascrapercontroller;

import de.demmer.dennis.odrascrapercontroller.entities.Article;
import de.demmer.dennis.odrascrapercontroller.repositories.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


import java.io.IOException;
import java.util.List;


@RestController
public class MainController {

    @Autowired
    ArticleRepository articleRepository;

    @GetMapping("/pikio")
    public String getPikio() throws IOException {

        final String uri = "http://odra-pikio-scraper:8080/pikio";

        RestTemplate restTemplate = new RestTemplate();
        Article[] result = restTemplate.getForObject(uri, Article[].class);

        System.out.println(result.length);
        for (Article art : result) {
            articleRepository.save(art);
        }

        return "Es wurden " + result.length + " Artikel von pikio empfangen und gespeichert";

    }

    @GetMapping("/spiegel")
    public String getSpiegel() throws IOException {

        final String uri = "http://odra-pikio-scraper:8080/spiegel";

        RestTemplate restTemplate = new RestTemplate();
        Article[] result = restTemplate.getForObject(uri, Article[].class);

        System.out.println(result.length);
        for (Article art : result) {
            articleRepository.save(art);
        }

        return "Es wurden " + result.length + " Artikel vom Spiegel scraper empfangen und gespeichert";

    }



}
