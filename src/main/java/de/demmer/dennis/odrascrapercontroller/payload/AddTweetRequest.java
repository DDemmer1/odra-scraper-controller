package de.demmer.dennis.odrascrapercontroller.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
public class AddTweetRequest {

    private long id;
    @NotBlank
    private String text;
    private String tweet_url;
    private String created_at;
    private String lang;
    private String user_id;
    private String user_profile_image_url;
    private String user_handle;
    private String user_name;


}
