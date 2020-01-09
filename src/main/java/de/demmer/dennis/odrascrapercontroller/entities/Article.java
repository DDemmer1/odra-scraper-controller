package de.demmer.dennis.odrascrapercontroller.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {

    @Id
    @GeneratedValue
    public int id;

    @Column(length = 10485760)
    private String headline;

    @Column(length = 10485760)
    private String textBody;

    @Column(length = 10485760)
    private String source;

    @Column(length = 10485760)
    private String source_name;

    @Column(length = 10485760)
    private String author;

    @Column(length = 10485760)
    private String topic;

    @Column(length = 10485760)
    private String link;

    private Date crawl_date;

    @Column(length = 10485760)
    private String creation_date;
}
