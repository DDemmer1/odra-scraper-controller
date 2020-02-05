package de.demmer.dennis.odrascrapercontroller.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class TwitterTrack {

    @Id
    @GeneratedValue
    private long id;

    private String hashtag;

    private int amount = 0;
}
