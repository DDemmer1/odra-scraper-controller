package de.demmer.dennis.odrascrapercontroller.repositories;

import de.demmer.dennis.odrascrapercontroller.entities.Article;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends CrudRepository<Article, Integer>{


    List<Article> findByLink(String link);

    List<Article> findBySourceName(String source);
    List<Article> findBySourceNameOrderByCrawlDateDesc(String source);
    List<Article> findByTextBodyContainingOrHeadlineContainingOrderByCrawlDateDesc(String queryTextBody, String queryHeadline);

//    @Query("SELECT a from articles WHERE a.crawlDate = current_date")
//    List<Article> findAllArticlesFromToday();

    long countAllBySourceName(String source);
}
