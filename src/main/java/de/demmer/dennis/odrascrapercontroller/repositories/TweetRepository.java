package de.demmer.dennis.odrascrapercontroller.repositories;

import de.demmer.dennis.odrascrapercontroller.entities.Tweet;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TweetRepository extends CrudRepository<Tweet, Long> {

    @Query(value = "SELECT t FROM Tweet t WHERE t.text LIKE ?1 ORDER BY t.id DESC ")
    List<Tweet> findAllWithContent(String content);



    List<Tweet> findAllByTextContainingIgnoreCaseOrderByIdDesc(String text);

}
