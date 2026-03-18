package com.ad.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VideoYoutube implements Video {
    private String videoId;
    private String title;
    private String description;
    private Instant publishedAt;
    private String channelId;
    private Boolean isMature;
    private String thumbnailUrl;
    private String duration;
    private Integer viewCount;
    private Integer likeCount;

    private Boolean oldEnough;
}

