package com.ad.dto.youtube;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class YoutubeSearchResponse {
    @JsonProperty("items")
    private List<ChannelSearchItem> items;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ChannelSearchItem {

        @JsonProperty("id")
        private ChannelId id;

        @JsonProperty("snippet")
        private ChannelSnippet snippet;

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class ChannelId {
            @JsonProperty("channelId")
            private String channelId;
        }

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class ChannelSnippet {
            @JsonProperty("title")
            private String title;

            @JsonProperty("description")
            private String description;

            @JsonProperty("channelTitle")
            private String channelTitle;

            @JsonProperty("publishedAt")
            private String publishedAt;
        }
    }
}
