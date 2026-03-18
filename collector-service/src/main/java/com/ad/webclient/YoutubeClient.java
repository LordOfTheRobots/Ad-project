package com.ad.webclient;

import com.ad.dto.youtube.YoutubeChannelResponse;
import com.ad.dto.youtube.YoutubeSearchResponse;
import com.ad.dto.youtube.YoutubeVideoResponse;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface YoutubeClient extends Client {

    @GetExchange(url = "search", accept = "application/json")
    YoutubeSearchResponse search(
            @RequestParam("q") String query,
            @RequestParam("key") String apiKey,
            @RequestParam(name = "relevanceLanguage", required = false) String languageOfChannel,
            @RequestParam(name = "regionCode", required = false) String region,
            @RequestParam(name = "part", defaultValue = "snippet") String part,
            @RequestParam(name = "type") String type,
            @RequestParam(name = "maxResults", defaultValue = "50") Integer maxResults
    );

    @GetExchange(url = "channels", accept = "application/json")
    YoutubeChannelResponse channel(
            @RequestParam("key") String apiKey,
            @RequestParam(name = "part", defaultValue = "snippet,statistics,contentDetails") String part,
            @RequestParam(name = "id", required = false) String channelId,
            @RequestParam(name = "forUsername", required = false) String username
    );

    @GetExchange("playlistItems")
    YoutubePlaylistItemResponse playlistItems(
            @RequestParam("key") String apiKey,
            @RequestParam(name = "part", defaultValue = "snippet,contentDetails") String part,
            @RequestParam("playlistId") String playlistId,
            @RequestParam(name = "maxResults", defaultValue = "50") int maxResults
    );
}
