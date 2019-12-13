package de.demmer.dennis.odrascrapercontroller.services;

import de.demmer.dennis.odrascrapercontroller.entities.Article;
import de.demmer.dennis.odrascrapercontroller.entities.Scraper;
import de.demmer.dennis.odrascrapercontroller.repositories.ArticleRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class ArticleService {

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    ScraperService scraperService;

    /**
     * Saves an Article in the database
     *
     * @param article
     * @return true if Article was saved
     */
    public boolean save(Article article) {
        if (article == null) {
            return false;
        }
        if (articleRepository.findByLink(article.getLink()).size() > 0) {
            return false;
        } else {
            articleRepository.save(article);
            return true;
        }
    }

    public Article findById(Integer id) {
        return articleRepository.findById(id).orElse(new Article());
    }


    public List<Article> findBySourceNameAndQuery(String sourceName, int limit, String query){
        if(query == null || query.equals("")){
            return findBySourceName(sourceName,limit);
        } else {
            List<Article> results = articleRepository.findByTextBodyContainingOrHeadlineContainingOrderByCrawlDateDesc(query,query);
            if(!sourceName.equals("all")){
                results.removeIf(article -> (!article.getSourceName().equals(sourceName)));
            }
            if(limit <= 0){
                return results;
            } else {
                int index = results.size() > limit ? limit : results.size();
                return results.subList(0,index);
            }

        }

    }

    public List<Article> findBySourceName(String sourceName, int limit) {
        if(limit <= 0){
            return articleRepository.findBySourceNameOrderByCrawlDateDesc(sourceName);
        }
        long numArticles = articleRepository.countAllBySourceName(sourceName);
        long index = numArticles > limit ? limit : numArticles;
        return articleRepository.findBySourceNameOrderByCrawlDateDesc(sourceName).subList(0, (int) index);
    }

    public long countAllBySourceName(String sourceName){
        return articleRepository.countAllBySourceName(sourceName);
    }

    public long count(){
        return articleRepository.count();
    }

    public List<Article> test() {
        List<Article> testArticles = new ArrayList<>();

        for (String name : scraperService.getAllScraperNames()) {
            List articelList = articleRepository.findBySourceName(name);
            if(articelList.size() > 0){
                testArticles.add(articleRepository.findBySourceName(name).get(0));
            }
        }

        return testArticles;
    }
}
