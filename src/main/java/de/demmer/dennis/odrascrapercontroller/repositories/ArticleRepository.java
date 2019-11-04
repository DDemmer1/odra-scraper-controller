package de.demmer.dennis.odrascrapercontroller.repositories;

import de.demmer.dennis.odrascrapercontroller.entities.Article;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends CrudRepository<Article, Integer>{


    List<Article> findByAuthor(String author);

}
