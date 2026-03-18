package com.ad.utils;

import com.ad.dto.youtube.YoutubeChannelResponse;
import com.ad.dto.youtube.YoutubeSearchResponse;
import com.ad.dto.youtube.YoutubeVideoResponse;
import com.ad.entity.Channel;
import com.ad.entity.ChannelYoutube;
import com.ad.entity.Video;
import com.ad.entity.VideoYoutube;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class YoutubeConverter {

    public List<Channel> toChannels(YoutubeSearchResponse response) {
        if (response == null || response.getItems() == null) {
            return List.of();
        }

        return response.getItems().stream()
                .filter(item -> item.getId() != null && item.getId().getChannelId() != null)
                .map(this::toChannel)
                .collect(Collectors.toList());
    }
    private Channel toChannel(YoutubeSearchResponse.ChannelSearchItem item) {
        ChannelYoutube channel = new ChannelYoutube();

        if (item.getId() != null) {
            channel.setChannelId(item.getId().getChannelId());
        }
        if (item.getSnippet() != null) {
            channel.setTitle(item.getSnippet().getTitle());
            channel.setDescription(item.getSnippet().getDescription());
            channel.setPublishedAt(Instant.parse(item.getSnippet().getPublishedAt()));
        }

        return channel;
    }

    public Video toVideoFromVideoResponse(YoutubeVideoResponse.VideoItem item) {
        VideoYoutube video = new VideoYoutube();
        video.setVideoId(item.getVideoId());

        if (item.getSnippet() != null) {
            video.setTitle(item.getSnippet().getTitle());
            video.setDescription(item.getSnippet().getDescription());
            video.setPublishedAt(Instant.parse(item.getSnippet().getPublishedAt()));
            video.setChannelId(item.getSnippet().getChannelId());
        }
        return video;
    }

    public Channel toChannelFromChannelResponse(YoutubeChannelResponse.ChannelItem item) {
        ChannelYoutube channel = new ChannelYoutube();
        channel.setChannelId(item.getChannelId());

        if (item.getSnippet() != null) {
            channel.setTitle(item.getSnippet().getTitle());
            channel.setRegion(item.getSnippet().getCountry());
        }

        return channel;
    }
}
