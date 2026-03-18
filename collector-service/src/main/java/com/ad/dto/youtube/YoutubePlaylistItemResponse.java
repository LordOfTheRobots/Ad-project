package com.ad.dto.youtube;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class YoutubePlaylistItemResponse {

    @JsonProperty("nextPageToken")
    private String nextPageToken;

    @JsonProperty("items")
    private List<PlaylistItem> items;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PlaylistItem {

        @JsonProperty("snippet")
        private Snippet snippet;

        @JsonProperty("contentDetails")
        private ContentDetails contentDetails;

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Snippet {
            @JsonProperty("publishedAt")
            private String publishedAt;

            @JsonProperty("title")
            private String title;

            @JsonProperty("description")
            private String description;

            @JsonProperty("channelId")
            private String channelId;

            @JsonProperty("channelTitle")
            private String channelTitle;

            @JsonProperty("position")
            private Integer position;
        }

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class ContentDetails {
            @JsonProperty("videoId")
            private String videoId;
        }
    }
}
