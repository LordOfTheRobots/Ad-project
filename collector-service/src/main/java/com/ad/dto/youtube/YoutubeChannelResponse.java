package com.ad.dto.youtube;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class YoutubeChannelResponse {

    @JsonProperty("items")
    private List<ChannelItem> items;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ChannelItem {

        @JsonProperty("id")
        private String channelId;

        @JsonProperty("snippet")
        private ChannelSnippet snippet;

        @JsonProperty("statistics")
        private ChannelStatistics statistics;

        @JsonProperty("contentDetails")
        private ChannelContentDetails contentDetails;

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class ChannelSnippet {
            @JsonProperty("title")
            private String title;

            @JsonProperty("description")
            private String description;

            @JsonProperty("customUrl")
            private String customUrl;

            @JsonProperty("publishedAt")
            private String publishedAt;

            @JsonProperty("country")
            private String country;
        }

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class ChannelStatistics {
            @JsonProperty("viewCount")
            private String viewCount;

            @JsonProperty("subscriberCount")
            private String subscriberCount;

            @JsonProperty("videoCount")
            private String videoCount;
        }

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class ChannelContentDetails {
            @JsonProperty("relatedPlaylists")
            private RelatedPlaylists relatedPlaylists;

            @Data
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class RelatedPlaylists {
                @JsonProperty("uploads")
                private String uploads;
            }
        }
    }
}
