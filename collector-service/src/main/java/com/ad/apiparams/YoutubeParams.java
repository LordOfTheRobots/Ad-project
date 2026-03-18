package com.ad.apiparams;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Builder
@Component
public class YoutubeParams implements Params {
    private String apiKey;
    private String regionCode;
    private String query;
    private String relevanceLanguage;
    private String part;
    private String type;
    private String channelId;
    private Integer maxResults;

    public YoutubeParams addPart(String part){
        this.part = part.concat(",").concat(part);
        return this;
    }
}
