package com.ad.collectorapi;

import com.ad.apiparams.Params;
import com.ad.entity.Channel;
import com.ad.entity.Video;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface Collector {
    List<Channel> serf(Params params);
    List<Video> requestCreatorsContent(Params params);
    Video requestVideos(Params params);
    Integer getMinimumTokens();
}

