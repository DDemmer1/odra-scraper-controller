package de.demmer.dennis.odrascrapercontroller.services;

import de.demmer.dennis.odrascrapercontroller.entities.Article;
import de.demmer.dennis.odrascrapercontroller.repositories.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    @Autowired
    ArticleRepository articleRepository;

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

}
