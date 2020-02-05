package de.demmer.dennis.odrascrapercontroller.repositories;

import de.demmer.dennis.odrascrapercontroller.entities.TwitterTrack;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TwitterTrackRepository extends CrudRepository<TwitterTrack, Long> {

    Optional<TwitterTrack> findTwitterTrackByHashtag(String hashtag);

}
