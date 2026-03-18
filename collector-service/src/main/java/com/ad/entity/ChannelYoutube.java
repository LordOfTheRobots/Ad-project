package com.ad.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
public class ChannelYoutube implements Channel {
    private String channelId;
    private String title;
    private String description;
    private String customUrl;
    private Instant publishedAt;
    private String region;
    private Integer subscriberCount;
}
