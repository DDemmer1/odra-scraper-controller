package de.demmer.dennis.odrascrapercontroller.controller;

import de.demmer.dennis.odrascrapercontroller.entities.Tweet;
import de.demmer.dennis.odrascrapercontroller.payload.AddTweetRequest;
import de.demmer.dennis.odrascrapercontroller.payload.ApiResponse;
import de.demmer.dennis.odrascrapercontroller.services.TwitterService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;


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
        hashtag = hashtag.trim().replaceAll("#","").replaceAll(" ","");;

        //handle multi hastag input
        String[] hashtags = hashtag.split(",");
        if(hashtags.length > 1){
            log.info("Multi hashtag input");
            boolean allTrue = true;
            for (String hashtagFromList: hashtags) {
                boolean response = twitterService.addTwitterTrack(hashtagFromList);
                if(response != true){
                    allTrue = false;
                }
            }
            if(allTrue){
                return new ApiResponse(true,"Track added");
            } else {
                return new ApiResponse(false,"Error. Track not added");
            }
        }

        if(twitterService.addTwitterTrack(hashtag)){
            return new ApiResponse(true,"Track added");
        } else {
            return new ApiResponse(false,"Error. Track not added");
        }
    }


    @GetMapping("/twitter/track/close/{hashtag}")
    public ApiResponse closeTweetTrack(@PathVariable String hashtag) {

        //handle multi hastag input
        String[] hashtags = hashtag.split(",");
        if(hashtags.length > 1){
            log.info("Multi hashtag input");
            boolean allTrue = true;
            for (String hashtagFromList: hashtags) {
                boolean response = twitterService.removeTrack(hashtagFromList);
                if(response != true){
                    allTrue = false;
                }
            }
            if(allTrue){
                return new ApiResponse(true,"Track closed");
            } else {
                return new ApiResponse(false,"Error. Track not closed");
            }
        }

        if(twitterService.removeTrack(hashtag)){
            return new ApiResponse(true,"Track closed");
        } else {
            return new ApiResponse(false,"Error. Track not closed");

        }
    }

    @GetMapping("/twitter/tweet/get/list/{hashtag}")
    public List<Tweet> getTweets(@PathVariable String hashtag){
        List<Tweet> tweetList = new ArrayList<>(twitterService.getTweetsWithHashtag(hashtag));
        Set<Tweet> tmp = new HashSet<>(tweetList);
        List<Tweet> tmpList = new ArrayList<>(tmp);
        Collections.sort(tmpList, new Comparator<Tweet>() {
            @Override
            public int compare(Tweet o1, Tweet o2) {
                return o2.getCreated_at().compareTo(o1.getCreated_at());
            }
        });
        return tmpList;
    }




}
