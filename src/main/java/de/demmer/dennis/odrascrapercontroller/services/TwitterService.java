package de.demmer.dennis.odrascrapercontroller.services;

import de.demmer.dennis.odrascrapercontroller.entities.Tweet;
import de.demmer.dennis.odrascrapercontroller.entities.TwitterTrack;
import de.demmer.dennis.odrascrapercontroller.payload.AddTweetRequest;
import de.demmer.dennis.odrascrapercontroller.repositories.TweetRepository;
import de.demmer.dennis.odrascrapercontroller.repositories.TwitterTrackRepository;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Log4j2
@Transactional
@Service
public class TwitterService {

    @Value("${twitter.scraper.url}")
    String twitterScraperUrl;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    TweetRepository tweetRepository;

    @Autowired
    TwitterTrackRepository twitterTrackRepository;

    private boolean sendRemoveTrackRequest(String hashtag){
        if (openURL(twitterScraperUrl + "/track/close/" + hashtag)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean sendAddTrackRequest(String hashtag){
        if (openURL(twitterScraperUrl + "/track/add/" + hashtag)) {
            return true;
        } else {
            return false;
        }
    }


    private boolean openURL(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            if (con.getResponseCode() == 200) {
                con.disconnect();
                return true;
            } else {
                return false;
            }

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }


    public void saveAsTweet(AddTweetRequest tweetRequest) {
        Tweet tweet = modelMapper.map(tweetRequest, Tweet.class);
        tweetRepository.save(tweet);
    }


    public boolean addTwitterTrack(String hashtag){

        TwitterTrack track = twitterTrackRepository.findTwitterTrackByHashtag(hashtag).orElse(new TwitterTrack());
        track.setAmount(track.getAmount()+1);
        track.setHashtag(hashtag);
        twitterTrackRepository.save(track);
        log.info("**ADD**");
        log.info("Hashtag:" + track.getHashtag());
        log.info("Amount: "+ track.getAmount());
        log.info("****");
        if(track.getAmount()==1){
            return sendAddTrackRequest(hashtag);
        } else {
            return true;
        }
    }

    public boolean removeTrack(String hashtag){
        try{
            TwitterTrack track = twitterTrackRepository.findTwitterTrackByHashtag(hashtag).orElse(new TwitterTrack());
            track.setAmount(track.getAmount()-1);
            log.info("**DELETE**");
            log.info("Hashtag:" + track.getHashtag());
            log.info("Amount: "+ track.getAmount());
            log.info("****");
            if(track.getAmount() <= 0) {
                twitterTrackRepository.delete(track);
                return sendRemoveTrackRequest(hashtag);
            } else {
                twitterTrackRepository.save(track);
                return true;
            }
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public void openAllTracks(){
        try {
            for (TwitterTrack track : twitterTrackRepository.findAll()) {
                sendAddTrackRequest(track.getHashtag());
            }
        } catch (Exception e){
            e.printStackTrace();
            openAllTracks();
        }

    }

    public long getTweetCount() {
        return tweetRepository.count();
    }

    public List<Tweet> getTweetsWithHashtag(String hashtag) {

        String[] hashtagList = hashtag.split(",");
        if(hashtagList.length > 1){
            List<Tweet> returnList = new ArrayList<>();
            for (String h : hashtagList) {
                returnList.addAll(getTweetsWithHashtag(h));
            }
            return returnList;
        }


        List<Tweet> toReturn = tweetRepository.findAllByTextContainingIgnoreCaseOrderByIdDesc(hashtag);
        int indexEnd = (toReturn.size() < 60) ? toReturn.size()-1 : 60;

        if(!toReturn.isEmpty()){
            return toReturn.subList(0, indexEnd);

        } else{
            return new ArrayList<Tweet>();
        }

    }
}
