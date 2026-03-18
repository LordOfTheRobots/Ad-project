package com.ad.collectorapi;

import com.ad.apiparams.Params;
import com.ad.apiparams.YoutubeParams;
import com.ad.dto.youtube.YoutubeChannelResponse;
import com.ad.dto.youtube.YoutubeSearchResponse;
import com.ad.entity.Channel;
import com.ad.entity.Video;
import com.ad.utils.YoutubeConverter;
import com.ad.webclient.Client;
import com.ad.webclient.YoutubeClient;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.util.ArrayList;
import java.util.List;

public class YoutubeCollector implements Collector{

    @Autowired
    private Client client;

    @Getter
    private Integer minimumTokens;

    @Autowired
    private YoutubeConverter converter;
    
    @Override
    @Retryable(
            includes = { HttpServerErrorException.class, ResourceAccessException.class },
            excludes = { IllegalArgumentException.class },
            maxRetries = 3,
            delay = 1000,
            multiplier = 2.0,
            maxDelay = 10000
    )
    public List<Channel> serf(Params params) {
        if (client instanceof YoutubeClient && params instanceof YoutubeParams newParams){
            YoutubeSearchResponse response = ((YoutubeClient) client).search(
                    newParams.getQuery(),
                    newParams.getApiKey(),
                    newParams.getRelevanceLanguage(),
                    newParams.getRegionCode(),
                    newParams.getPart(),
                    newParams.getType(),
                    newParams.getMaxResults()
            );
            return converter.toChannels(response);
        }
        return new ArrayList<Channel>();
    }

    @Retryable(
            includes = { HttpServerErrorException.class, ResourceAccessException.class },
            excludes = { IllegalArgumentException.class },
            maxRetries = 3,
            delay = 1000,
            multiplier = 2.0,
            maxDelay = 10000
    )
    @Override
    public List<Video> requestCreatorsContent(Params params) {
        if (client instanceof YoutubeClient && params instanceof YoutubeParams newParams){
            YoutubeChannelResponse response = ((YoutubeClient) client).channel(
                    newParams.getApiKey(),
                    newParams.getPart(),
                    newParams.getChannelId(),
                    null
            );
            return converter.toCha(response);
        }
        return "";
    }

    @Override
    public Video requestVideos(Params params) {
        return null;
    }

    @Retryable(
            includes = { HttpServerErrorException.class, ResourceAccessException.class },
            excludes = { IllegalArgumentException.class },
            maxRetries = 3,
            delay = 1000,
            multiplier = 2.0,
            maxDelay = 10000
    )
    public
}
