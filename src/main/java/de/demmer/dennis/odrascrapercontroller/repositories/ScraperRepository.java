package de.demmer.dennis.odrascrapercontroller.repositories;

import de.demmer.dennis.odrascrapercontroller.entities.Scraper;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScraperRepository extends CrudRepository<Scraper, Integer> {



}
