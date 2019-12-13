package de.demmer.dennis.odrascrapercontroller.entities;

import lombok.Data;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Data
@Entity
@Table(name = "scraper")
public class Scraper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    @Column(length = 60)
    private String url;

    private String name;


}
