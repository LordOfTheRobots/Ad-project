package com.ad.dto.youtube;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class YoutubeVideoResponse {

    @JsonProperty("items")
    private List<VideoItem> items;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class VideoItem {

        @JsonProperty("id")
        private String videoId;

        @JsonProperty("snippet")
        private VideoSnippet snippet;

        @JsonProperty("statistics")
        private Statistics statistics;

        @JsonProperty("contentDetails")
        private ContentDetails contentDetails;

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class VideoSnippet {
            @JsonProperty("publishedAt")
            private String publishedAt;

            @JsonProperty("channelId")
            private String channelId;

            @JsonProperty("title")
            private String title;

            @JsonProperty("description")
            private String description;

            @JsonProperty("channelTitle")
            private String channelTitle;

            @JsonProperty("tags")
            private List<String> tags;

            @JsonProperty("categoryId")
            private String categoryId;

            @JsonProperty("liveBroadcastContent")
            private String liveBroadcastContent;
        }

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Statistics {
            @JsonProperty("viewCount")
            private String viewCount;

            @JsonProperty("likeCount")
            private String likeCount;

            @JsonProperty("commentCount")
            private String commentCount;
        }

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class ContentDetails {
            @JsonProperty("duration")
            private String duration;

            @JsonProperty("definition")
            private String definition;

            @JsonProperty("caption")
            private String caption;
        }
    }
}
