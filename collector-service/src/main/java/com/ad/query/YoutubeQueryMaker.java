package com.ad.query;

import com.ad.entity.APIKey;
import com.ad.apiparams.YoutubeParams;
import com.ad.entity.Channel;
import com.ad.entity.Video;
import com.ad.utils.ChannelUrn;
import com.ad.utils.VideoUrn;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.ad.apiparams.Params;
import java.util.ArrayList;
import java.util.Collections;

@Component
public class YoutubeQueryMaker implements QueryMaker {
    @Value("${key.words}")
    private ArrayList<String> keyWords;

    @Autowired
    private ChannelUrn channels;

    @Autowired
    private ObjectFactory<YoutubeParams> paramsFactory;

    @Autowired
    private VideoUrn videos;

    public Params makeQuery(RequestAction action, APIKey apiKey){
        return switch (action){
            case SEARCH -> search(apiKey);
            case TAKE_CHANNEL -> takeChannel(apiKey);
            case TAKE_VIDEO -> takeVideo(apiKey);
            default -> throw new IllegalStateException("No such action request: " + action);
        };
    }

    private Params search(APIKey apiKey){
        YoutubeParams params = paramsFactory.getObject();
        if (!keyWords.isEmpty()) {
            Collections.shuffle(keyWords);
            params.setQuery(keyWords.get(0) + " " + keyWords.get(1));
        }
        params.addPart("id");
        params.setType("channel");
        params.setApiKey(apiKey.getKey());
        params.setMaxResults(50);
        return params;
    }

    private Params takeChannel(APIKey apiKey){
        YoutubeParams params = paramsFactory.getObject();
        params.addPart("statistics").addPart("id").addPart("contentDetails");
        Channel channel = channels.poll();
        if (channel == null) throw new IllegalStateException("No channels in queue");

        params.setChannelId(channel.getChannelId());
        params.setApiKey(apiKey.getKey());
        return params;
    }

    private Params takeVideo(APIKey apiKey){
        YoutubeParams params = paramsFactory.getObject();
        params.addPart("statistics").addPart("contentDetails");
        Video video = videos.poll();
        if (video == null) throw new IllegalStateException("No videos in queue");

        params.setChannelId(video.getVideoId());
        params.setApiKey(apiKey.getKey());
        return params;
    }
}
