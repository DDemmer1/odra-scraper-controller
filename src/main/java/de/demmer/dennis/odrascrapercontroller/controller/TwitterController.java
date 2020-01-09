package de.demmer.dennis.odrascrapercontroller.controller;

import de.demmer.dennis.odrascrapercontroller.entities.Tweet;
import de.demmer.dennis.odrascrapercontroller.payload.AddTweetRequest;
import de.demmer.dennis.odrascrapercontroller.payload.ApiResponse;
import de.demmer.dennis.odrascrapercontroller.services.TwitterService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Log4j2
@RestController
public class TwitterController {

    @Autowired
    TwitterService twitterService;


    @PostMapping("/twitter/tweet/add")
    public ApiResponse addTweet(@Valid @RequestBody AddTweetRequest tweetRequest) {
        log.info(tweetRequest.getText());
        twitterService.saveAsTweet(tweetRequest);
        long count = twitterService.getTweetCount();
        log.info(count);
        return new ApiResponse(true, "Tweet added");
    }


    @GetMapping("/twitter/track/add/{hashtag}")
    public ApiResponse addTweetTrack(@PathVariable String hashtag) {
        if(twitterService.addTrack(hashtag)){
            return new ApiResponse(true,"Track added");
        } else {
            return new ApiResponse(false,"Error. Track not added");
        }
    }


    @GetMapping("/twitter/track/close/{hashtag}")
    public ApiResponse closeTweetTrack(@PathVariable String hashtag) {
        if(twitterService.closeTrack(hashtag)){
            return new ApiResponse(true,"Track closed");
        } else {
            return new ApiResponse(false,"Error. Track not closed");

        }
    }

    @GetMapping("/twitter/tweet/get/list/{hashtag}")
    public List<Tweet> getTweets(@PathVariable String hashtag){
        return twitterService.getTweetsWithHastag(hashtag);

    }




}
