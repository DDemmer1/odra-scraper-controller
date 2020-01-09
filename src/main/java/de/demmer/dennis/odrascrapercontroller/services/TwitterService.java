package de.demmer.dennis.odrascrapercontroller.services;

import de.demmer.dennis.odrascrapercontroller.entities.Tweet;
import de.demmer.dennis.odrascrapercontroller.payload.AddTweetRequest;
import de.demmer.dennis.odrascrapercontroller.repositories.TweetRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

@Service
public class TwitterService {

    @Value("${twitter.scraper.url}")
    String twitterScraperUrl;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    TweetRepository tweetRepository;

    public boolean closeTrack(String hashtag){
        if (openURL(twitterScraperUrl + "/track/close/" + hashtag)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean addTrack(String hashtag){
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

    public long getTweetCount() {
        return tweetRepository.count();
    }

    public List<Tweet> getTweetsWithHastag(String hashtag) {
//        hashtag = "%#" + hashtag +"%";
//        return tweetRepository.findAllWithContent(hashtag);

        return tweetRepository.findAllByTextContainingIgnoreCaseOrderByIdDesc(hashtag);



    }
}
