package de.demmer.dennis.odrascrapercontroller.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tweets")
public class Tweet {

    @Id
    private long id;

    @Lob
    @Column( length = 100000 )
    private String text;

    private String tweet_url;
    private String created_at;
    private String lang;
    private String user_id;
    private String user_profile_image_url;
    private String user_handle;
    private String user_name;


}
